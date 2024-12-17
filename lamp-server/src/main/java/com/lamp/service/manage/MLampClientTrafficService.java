package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampClientTraffic;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServiceMonth;
import com.lamp.mapper.LampClientTrafficMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MLampClientTrafficService extends ServiceImpl<LampClientTrafficMapper, LampClientTraffic> {

    @Autowired
    private LampClientTrafficMapper clientTrafficMapper;

    public void setClientTrafficList(LampServiceMonth serviceMonth) {
        LambdaQueryWrapper<LampClientTraffic> queryWrapper = new LambdaQueryWrapper<>(LampClientTraffic.class);
        queryWrapper.eq(LampClientTraffic::getServiceMonthId, serviceMonth.getId());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampClientTraffic> clientTrafficList = clientTrafficMapper.selectList(queryWrapper);
        serviceMonth.setClientTrafficList(clientTrafficList);
        serviceMonth.calculateClientTraffic();
    }

    public void setClientTrafficList(LampInbound inbound) {
        LambdaQueryWrapper<LampClientTraffic> queryWrapper = new LambdaQueryWrapper<>(LampClientTraffic.class);
        queryWrapper.eq(LampClientTraffic::getInboundId, inbound.getId());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampClientTraffic> clientTrafficList = clientTrafficMapper.selectList(queryWrapper);
        inbound.setClientTrafficList(clientTrafficList);
    }
}
