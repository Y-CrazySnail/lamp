package com.lamp.service.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampOrder;
import com.lamp.entity.LampProduct;
import com.lamp.entity.LampRewardRecord;
import com.lamp.im.dto.SysTelegramSendDTO;
import com.lamp.im.service.ISysTelegramService;
import com.lamp.infrastructure.payment.EPaymentProcessor;
import com.lamp.mapper.LampOrderMapper;
import com.lamp.schedule.GlobalData;
import com.lamp.schedule.SyncParam;
import com.lamp.service.manage.MLampServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class LampOrderService extends ServiceImpl<LampOrderMapper, LampOrder> {

    @Autowired
    private LampProductService productService;

    @Autowired
    private LampMemberService memberService;

    @Autowired
    private LampRewardRecordService rewardRecordService;

    @Autowired
    private MLampServerService serverService;

    @Autowired
    private ISysTelegramService sysTelegramService;

    @Autowired
    private EPaymentProcessor ePaymentProcessor;

    public void setOrderList(LampMember member) {
        LambdaQueryWrapper<LampOrder> queryWrapper = new LambdaQueryWrapper<>(LampOrder.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(LampOrder::getMemberId, member.getId());
        queryWrapper.orderByDesc(LampOrder::getId);
        member.setOrderList(list(queryWrapper));
    }

    public List<LampOrder> listByMemberId(Long memberId) {
        LambdaQueryWrapper<LampOrder> queryWrapper = new LambdaQueryWrapper<>(LampOrder.class);
        queryWrapper.eq(LampOrder::getMemberId, memberId);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return list(queryWrapper);
    }

    public LampOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<LampOrder> queryWrapper = new LambdaQueryWrapper<>(LampOrder.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(LampOrder::getOrderNo, orderNo);
        return getOne(queryWrapper);
    }

    /**
     * 下单
     *
     * @param order 订单
     */
    @Transactional
    public void place(LampOrder order) {
        LampProduct product = productService.getById(order.getPackageId());
        order.createOrder(product.getBandwidth(), product.getPeriod(), product.getPrice(), product.getType());
        LampMember member = memberService.getById(order.getMemberId());
        if (Objects.nonNull(member.getBalance())) {
            if (member.getBalance().compareTo(order.getPrice().subtract(BigDecimal.valueOf(2))) <= 0) {
                order.setDeductBalance(member.getBalance());
            } else {
                order.setDeductBalance(order.getPrice().subtract(BigDecimal.valueOf(2)));
            }
        }
        save(order);
    }

    /**
     * 调起支付
     *
     * @param order 订单信息
     * @return 支付信息
     */
    public JsonNode pay(LampOrder order) {
        order = getById(order.getId());
        LampMember member = memberService.getById(order.getMemberId());
        if (member.getBalance().compareTo(order.getDeductBalance()) < 0) {
            log.info("用户：{}-订单：{}-余额不足", member, order);
            throw new RuntimeException("余额不足");
        }
        JsonNode payRes = ePaymentProcessor.prepay(order.getPrice().subtract(order.getDeductBalance()), order.getOrderNo());
        if ("1".equals(payRes.get("code").toString())) {
            order.setTradeNo(payRes.get("trade_no").toString().replace("\"", ""));
            super.updateById(order);
        }
        return payRes;
    }

    @Transactional
    public void finish(LampOrder order) {
        order = getByOrderNo(order.getOrderNo());
        if (LampOrder.STATUS.ED.getValue().equals(order.getStatus())) {
            // 已支付
            return;
        }
        LampMember member = memberService.getById(order.getMemberId());
        if (LampOrder.TYPE_DATA.equals(order.getType())) {
            // 增值服务-数据包
            member.setMonthBandwidth(order.getBandwidth() + member.getMonthBandwidth());
        } else {
            // 基础服务
            if (member.getBandwidth().equals(order.getBandwidth())) {
                log.info("套餐流量:{}相同, 增加时间", order.getBandwidth());
                // 计划相同
                member.addMonths(order.getPeriod());
            } else {
                // 计划不同
                log.info("套餐流量不相同, 从:{}转换为:{}", member.getBandwidth(), order.getBandwidth());
                member.addMonths(order.getPeriod());
            }
            member.setBandwidth(order.getBandwidth());
            member.resetBandwidth();
        }
        member.setTotalSpent(member.getTotalSpent().add(order.getPrice()));
        member.setBalance(member.getBalance().subtract(order.getDeductBalance()));
        member.calculateLevel();
        memberService.updateById(member);
        // 计算奖励机制
        // 当前用户推荐人
        LampMember referrerMember = null;
        if (StrUtil.isNotEmpty(member.getReferrerCode())) {
            referrerMember = memberService.getByReferralCode(member.getReferrerCode());
        }
        if (Objects.nonNull(referrerMember)) {
            LampRewardRecord rewardRecord = LampRewardRecord.init(referrerMember, member, order);
            referrerMember.addBalance(rewardRecord.getRewardAmount());
            rewardRecordService.save(rewardRecord);
            memberService.updateById(referrerMember);
        }
        // TG消息通知
        try {
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("purchase");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            // 常规套餐购买：
            // 微信：#{wechat}
            // 邮箱：#{email}
            // 到期：#{expiryDate}
            // 时长：#{period}个月
            // 价格：#{price}元
            // 流量：#{bandwidth}GB
            // 请留意Crisp客服消息！
            replaceMap.put("wechat", member.getWechat());
            replaceMap.put("email", member.getEmail());
            replaceMap.put("expiryDate", member.getExpiryDate());
            replaceMap.put("period", order.getPeriod());
            replaceMap.put("bandwidth", order.getBandwidth() / 1024 / 1024 / 1024);
            replaceMap.put("price", order.getPrice());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
        order.finish();
        super.updateById(order);
        SyncParam syncParam = new SyncParam();
        syncParam.setMember(member);
        GlobalData.SYNC_QUEUE.add(syncParam);
    }
}
