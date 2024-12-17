package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServer;
import com.lamp.mapper.LampInboundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class MLampInboundService extends ServiceImpl<LampInboundMapper, LampInbound> {

    @Autowired
    private LampInboundMapper inboundMapper;

    @Autowired
    private MLampSubscriptionService subscriptionService;

    @Autowired
    private MLampClientTrafficService clientTrafficService;

    @Override
    public boolean saveBatch(Collection<LampInbound> entityList) {
        super.saveBatch(entityList);
        for (LampInbound inbound : entityList) {
            if (Objects.nonNull(inbound.getSubscriptionList()) && !inbound.getSubscriptionList().isEmpty()) {
                inbound.getSubscriptionList().forEach(subscription -> subscription.setInboundId(inbound.getId()));
                subscriptionService.saveBatch(inbound.getSubscriptionList());
            }
        }
        return true;
    }

    public void setInboundList(LampServer server) {
        LambdaQueryWrapper<LampInbound> queryWrapper = new LambdaQueryWrapper<>(LampInbound.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(LampInbound::getServerId, server.getId());
        List<LampInbound> inboundList = inboundMapper.selectList(queryWrapper);
        if (Objects.nonNull(inboundList) && !inboundList.isEmpty()) {
            for (LampInbound inbound : inboundList) {
                subscriptionService.setSubscriptionList(inbound);
            }
        }
        server.setInboundList(inboundList);
    }
}
