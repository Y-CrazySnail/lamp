package com.yeem.lamp.domain.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.security.Constant;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinMemberEntity;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinOrder;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinPackage;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinService;
import com.yeem.lamp.infrastructure.persistence.mapper.AladdinOrderMapper;
import com.yeem.lamp.domain.service.IAladdinMemberService;
import com.yeem.lamp.domain.service.IAladdinOrderService;
import com.yeem.lamp.domain.service.IAladdinPackageService;
import com.yeem.lamp.domain.service.IAladdinServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Slf4j
@Service
public class AladdinOrderServiceImpl extends ServiceImpl<AladdinOrderMapper, AladdinOrder> implements IAladdinOrderService {

    @Autowired
    private AladdinOrderMapper aladdinOrderMapper;
    @Autowired
    private IAladdinPackageService aladdinPackageService;
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
        UpdateWrapper<AladdinOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }

    @Override
    public IPage<AladdinOrder> pages(int current, int size) {
        QueryWrapper<AladdinOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<AladdinOrder> page = new Page<>(current, size);
        return aladdinOrderMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<AladdinOrder> listByMemberId(Long memberId) {
        QueryWrapper<AladdinOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.orderByDesc(BaseEntity.BaseField.CREATE_TIME.getName());
        return super.list(queryWrapper);
    }

    @Override
    public void place(AladdinOrder aladdinOrder) {
        AladdinPackage aladdinPackage = aladdinPackageService.getById(aladdinOrder.getPackageId());
        String orderNo = "No" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + aladdinOrder.getMemberId();
        aladdinOrder.setOrderNo(orderNo);
        aladdinOrder.setOrderTime(new Date());
        aladdinOrder.setStatus("-1");
        aladdinOrder.setDataTraffic(aladdinPackage.getDataTraffic());
        aladdinOrder.setPeriod(aladdinPackage.getPeriod());
        aladdinOrder.setPrice(aladdinPackage.getPrice());
        super.save(aladdinOrder);
    }

    @Override
    @Transactional
    public String pay(AladdinOrder aladdinOrder) {
        aladdinOrder = aladdinOrderMapper.selectById(aladdinOrder.getId());
        String merchantApi = environment.getProperty("merchant.api");
        if (StrUtil.isEmpty(merchantApi)) {
            log.error("未获取到商户API");
            return null;
        }
        String merchantId = environment.getProperty("merchant.id");
        if (StrUtil.isEmpty(merchantId)) {
            log.error("未获取到商户ID");
            return null;
        }
        String merchantKey = environment.getProperty("merchant.key");
        String notifyUrl = environment.getProperty("merchant.notify-url");
        String returnUrl = environment.getProperty("merchant.return-url");
        String sign = "clientip=" + "aladdinslamp.cc"
                + "&device=" + "pc"
                + "&money=" + aladdinOrder.getPrice()
                + "&name=" + "VIP会员"
                + "&notify_url=" + notifyUrl
                + "&out_trade_no=" + aladdinOrder.getOrderNo()
                + "&pid=" + merchantId
                + "&return_url=" + returnUrl
                + "&type=" + "alipay" + merchantKey;
        String md5Hex = MD5.create().digestHex(sign);
        Map<String, String> form = new HashMap<>();
        HttpResponse response = HttpRequest.post(merchantApi)
                .form("pid", merchantId)
                .form("type", "alipay")
                .form("out_trade_no", aladdinOrder.getOrderNo())
                .form("notify_url", notifyUrl)
                .form("return_url", returnUrl)
                .form("name", "VIP会员")
                .form("money", aladdinOrder.getPrice())
                .form("clientip", "aladdinslamp.cc")
                .form("device", "pc")
                .form("sign", md5Hex)
                .form("sign_type", "MD5")
                .execute();
        log.info("{}", response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            if ("1".equals(jsonNode.get("code").toString())) {
                aladdinOrder.setTradeNo(jsonNode.get("trade_no").toString().replace("\"", ""));
                aladdinOrderMapper.updateById(aladdinOrder);
            }
        } catch (IOException e) {
            log.error("update order info error");
        }
        return response.body();
    }

    @Override
    @Transactional
    public void finish(AladdinOrder aladdinOrder) {
        QueryWrapper<AladdinOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", aladdinOrder.getOrderNo());
        queryWrapper.eq("trade_no", aladdinOrder.getTradeNo());
        aladdinOrder = aladdinOrderMapper.selectOne(queryWrapper);
        try {
            AladdinMemberEntity aladdinMember = aladdinMemberService.getById(aladdinOrder.getMemberId());
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("purchase");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            // 用户：#{email}购买了【时长：#{period}】-【流量：#{dataTraffic}】的【#{price}】元套餐，请注意Crisp客服消息！
            replaceMap.put("email", aladdinMember.getEmail());
            replaceMap.put("period", aladdinOrder.getPeriod());
            replaceMap.put("dataTraffic", aladdinOrder.getDataTraffic());
            replaceMap.put("price", aladdinOrder.getPrice());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
        if ("1".equals(aladdinOrder.getStatus())) {
            log.info("finish order：{}", aladdinOrder.getId());
            return;
        }
        List<AladdinService> aladdinServiceList = aladdinServiceService.listByMemberId(aladdinOrder.getMemberId());
        Long serviceId = null;
        for (AladdinService aladdinService : aladdinServiceList) {
            if (aladdinOrder.getDataTraffic().equals(aladdinService.getDataTraffic())) {
                serviceId = aladdinService.getId();
                if (aladdinService.getEndDate().before(new Date())) {
                    aladdinService.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(aladdinOrder.getPeriod())));
                } else {
                    aladdinService.setEndDate(DateUtil.offsetMonth(aladdinService.getEndDate(), Integer.parseInt(aladdinOrder.getPeriod())));
                }
                aladdinService.setPeriod(aladdinOrder.getPeriod());
                aladdinService.setPrice(aladdinOrder.getPrice());
                aladdinServiceService.updateById(aladdinService);
                break;
            }
        }
        if (null == serviceId) {
            AladdinService aladdinService = new AladdinService();
            aladdinService.setMemberId(aladdinOrder.getMemberId());
            aladdinService.setBeginDate(new Date());
            aladdinService.setEndDate(DateUtil.offsetMonth(new Date(), Integer.parseInt(aladdinOrder.getPeriod())).toJdkDate());
            aladdinService.setDataTraffic(aladdinOrder.getDataTraffic());
            aladdinService.setPeriod(aladdinOrder.getPeriod());
            aladdinService.setPrice(aladdinOrder.getPrice());
            aladdinService.setUuid(UUID.fastUUID().toString());
            aladdinService.setStatus("0");
            aladdinServiceService.save(aladdinService);
            serviceId = aladdinService.getId();
        }
        // 更新状态
        aladdinOrder.setServiceId(serviceId);
        aladdinOrder.setCompleteTime(new Date());
        aladdinOrder.setStatus("1");
        log.info("service id:{}", aladdinOrder.getId());
        aladdinOrderMapper.updateById(aladdinOrder);
        xuiService.sync();
    }
}
