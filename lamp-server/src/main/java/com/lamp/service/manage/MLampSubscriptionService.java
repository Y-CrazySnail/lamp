package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampSubscription;
import com.lamp.mapper.LampSubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MLampSubscriptionService extends ServiceImpl<LampSubscriptionMapper, LampSubscription> {

    @Autowired
    private LampSubscriptionMapper subscriptionMapper;

    public void setSubscriptionList(LampInbound inbound) {
        LambdaQueryWrapper<LampSubscription> queryWrapper = new LambdaQueryWrapper<>(LampSubscription.class);
        queryWrapper.eq(LampSubscription::getInboundId, inbound.getId());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampSubscription> subscriptionList = subscriptionMapper.selectList(queryWrapper);
        inbound.setSubscriptionList(subscriptionList);
    }
}
