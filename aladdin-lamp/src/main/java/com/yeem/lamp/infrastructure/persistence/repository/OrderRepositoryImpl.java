package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.repository.OrderRepository;
import com.yeem.lamp.domain.repository.PackageRepository;
import com.yeem.lamp.infrastructure.persistence.entity.*;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.OrderMapper;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServiceService;
import com.yeem.lamp.security.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private IAladdinMemberService aladdinMemberService;
    @Autowired
    private ISysTelegramService sysTelegramService;
    @Autowired
    private XUIService xuiService;
    @Autowired
    private Environment environment;

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<OrderDo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }

    @Override
    public IPage<OrderDo> pages(int current, int size) {
        QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<OrderDo> page = new Page<>(current, size);
        return orderMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<OrderDo> listByMemberId(Long memberId) {
        QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.orderByDesc(BaseEntity.BaseField.CREATE_TIME.getName());
        return super.list(queryWrapper);
    }



    @Override
    @Transactional
    public void finish(OrderDo orderDo) {
        QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderDo.getOrderNo());
        queryWrapper.eq("trade_no", orderDo.getTradeNo());
        orderDo = orderMapper.selectOne(queryWrapper);
        try {
            MemberDo aladdinMember = aladdinMemberService.getById(orderDo.getMemberId());
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("purchase");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            // 用户：#{email}购买了【时长：#{period}】-【流量：#{dataTraffic}】的【#{price}】元套餐，请注意Crisp客服消息！
            replaceMap.put("email", aladdinMember.getEmail());
            replaceMap.put("period", orderDo.getPeriod());
            replaceMap.put("dataTraffic", orderDo.getDataTraffic());
            replaceMap.put("price", orderDo.getPrice());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
        if ("1".equals(orderDo.getStatus())) {
            log.info("finish order：{}", orderDo.getId());
            return;
        }
        List<ServiceDo> serviceDoList = aladdinServiceService.listByMemberId(orderDo.getMemberId());
        Long serviceId = null;
        for (ServiceDo serviceDo : serviceDoList) {
            if (orderDo.getDataTraffic().equals(serviceDo.getDataTraffic())) {
                serviceId = serviceDo.getId();
                if (serviceDo.getEndDate().before(new Date())) {
                    serviceDo.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(orderDo.getPeriod())));
                } else {
                    serviceDo.setEndDate(DateUtil.offsetMonth(serviceDo.getEndDate(), Integer.parseInt(orderDo.getPeriod())));
                }
                serviceDo.setPeriod(orderDo.getPeriod());
                serviceDo.setPrice(orderDo.getPrice());
                aladdinServiceService.updateById(serviceDo);
                break;
            }
        }
        if (null == serviceId) {
            ServiceDo serviceDo = new ServiceDo();
            serviceDo.setMemberId(orderDo.getMemberId());
            serviceDo.setBeginDate(new Date());
            serviceDo.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(orderDo.getPeriod())).toJdkDate());
            serviceDo.setDataTraffic(orderDo.getDataTraffic());
            serviceDo.setPeriod(orderDo.getPeriod());
            serviceDo.setPrice(orderDo.getPrice());
            serviceDo.setUuid(UUID.fastUUID().toString());
            serviceDo.setStatus("0");
            aladdinServiceService.save(serviceDo);
            serviceId = serviceDo.getId();
        }
        // 更新状态
        orderDo.setServiceId(serviceId);
        orderDo.setCompleteTime(new Date());
        orderDo.setStatus("1");
        log.info("service id:{}", orderDo.getId());
        orderMapper.updateById(orderDo);
        xuiService.sync();
    }
}
