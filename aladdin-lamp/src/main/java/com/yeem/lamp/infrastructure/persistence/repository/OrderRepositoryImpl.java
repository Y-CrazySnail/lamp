package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.repository.OrderRepository;
import com.yeem.lamp.infrastructure.persistence.entity.*;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.OrderMapper;
import com.yeem.lamp.security.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<OrderDo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
//        return super.update(updateWrapper);
        return true;
    }

    @Override
    public void updateById(Order order) {
        OrderDo orderDo = new OrderDo(order);
        orderMapper.updateById(orderDo);
    }

    @Override
    public Order getById(Long id) {
        OrderDo orderDo = orderMapper.selectById(id);
        return orderDo.convertOrder();
    }

    @Override
    public List<Order> list() {
        LambdaQueryWrapper<OrderDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDo::getDeleteFlag, false);
        List<OrderDo> orderDoList = orderMapper.selectList(queryWrapper);
        return orderDoList.stream().map(OrderDo::convertOrder).collect(Collectors.toList());
    }

    @Override
    public IPage<Order> pages(int current, int size) {
        QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<OrderDo> page = new Page<>(current, size);
        page = orderMapper.selectPage(page, queryWrapper);
        IPage<Order> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(OrderDo::convertOrder).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    @Override
    public List<Order> listByMemberId(Long memberId) {
        QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.orderByDesc(BaseEntity.BaseField.CREATE_TIME.getName());
        List<OrderDo> orderDoList = orderMapper.selectList(queryWrapper);
        return orderDoList.stream().map(OrderDo::convertOrder).collect(Collectors.toList());
    }

    @Override
    public void place(OrderDo orderDo) {

    }

    @Override
    public String pay(OrderDo orderDo) {
        return null;
    }

    @Override
    @Transactional
    public void finish(Order order) {
        QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", order.getOrderNo());
        queryWrapper.eq("trade_no", order.getTradeNo());
        OrderDo orderDo = orderMapper.selectOne(queryWrapper);
//        try {
//            MemberDo aladdinMember = aladdinMemberService.getById(orderDo.getMemberId());
//            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
//            sysTelegramSendDTO.setTemplateName("purchase");
//            sysTelegramSendDTO.setTemplateType("telegram");
//            Map<String, Object> replaceMap = new HashMap<>();
//            // 用户：#{email}购买了【时长：#{period}】-【流量：#{dataTraffic}】的【#{price}】元套餐，请注意Crisp客服消息！
//            replaceMap.put("email", aladdinMember.getEmail());
//            replaceMap.put("period", orderDo.getPeriod());
//            replaceMap.put("dataTraffic", orderDo.getDataTraffic());
//            replaceMap.put("price", orderDo.getPrice());
//            sysTelegramSendDTO.setReplaceMap(replaceMap);
//            sysTelegramService.send(sysTelegramSendDTO);
//        } catch (Exception e) {
//            log.error("send telegram message error:", e);
//        }
        if ("1".equals(orderDo.getStatus())) {
            log.info("finish order：{}", orderDo.getId());
            return;
        }
//        List<ServiceDo> serviceDoList = aladdinServiceService.listByMemberId(orderDo.getMemberId());
//        Long serviceId = null;
//        for (ServiceDo serviceDo : serviceDoList) {
//            if (orderDo.getDataTraffic().equals(serviceDo.getDataTraffic())) {
//                serviceId = serviceDo.getId();
//                if (serviceDo.getEndDate().before(new Date())) {
//                    serviceDo.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(orderDo.getPeriod())));
//                } else {
//                    serviceDo.setEndDate(DateUtil.offsetMonth(serviceDo.getEndDate(), Integer.parseInt(orderDo.getPeriod())));
//                }
//                serviceDo.setPeriod(orderDo.getPeriod());
//                serviceDo.setPrice(orderDo.getPrice());
//                aladdinServiceService.updateById(serviceDo);
//                break;
//            }
//        }
//        if (null == serviceId) {
//            ServiceDo serviceDo = new ServiceDo();
//            serviceDo.setMemberId(orderDo.getMemberId());
//            serviceDo.setBeginDate(new Date());
//            serviceDo.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(orderDo.getPeriod())).toJdkDate());
//            serviceDo.setDataTraffic(orderDo.getDataTraffic());
//            serviceDo.setPeriod(orderDo.getPeriod());
//            serviceDo.setPrice(orderDo.getPrice());
//            serviceDo.setUuid(UUID.fastUUID().toString());
//            serviceDo.setStatus("0");
//            aladdinServiceService.save(serviceDo);
//            serviceId = serviceDo.getId();
//        }
//        // 更新状态
//        orderDo.setServiceId(serviceId);
        orderDo.setCompleteTime(new Date());
        orderDo.setStatus("1");
        log.info("service id:{}", orderDo.getId());
        orderMapper.updateById(orderDo);
//        xuiService.sync();
    }

    @Override
    public void insert(Order order) {
        OrderDo orderDo = new OrderDo(order);
        orderMapper.insert(orderDo);
    }
}
