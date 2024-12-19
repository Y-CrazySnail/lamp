package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServer;
import com.lamp.entity.LampServiceMonth;
import com.lamp.mapper.LampInboundMapper;
import com.lamp.xui.XServer;
import com.lamp.xui.builder.XuiInboundBuilder;
import com.lamp.xui.entity.XuiInbound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MLampInboundService extends ServiceImpl<LampInboundMapper, LampInbound> {

    @Autowired
    private LampInboundMapper inboundMapper;

    @Autowired
    private MLampServerService serverService;

    @Autowired
    private MLampSubscriptionService subscriptionService;

    @Autowired
    private MLampClientTrafficService clientTrafficService;

    @Autowired
    private MLampServiceMonthService serviceMonthService;

    @Override
    public LampInbound getById(Serializable id) {
        LampInbound inbound = inboundMapper.selectById(id);
        LampServer server = serverService.getById(inbound.getServerId());
        XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
        List<XuiInbound> xuiInboundList = xServer.inboundList(inbound.getXuiId());
        if (xuiInboundList.isEmpty()) {
            return null;
        }
        return inbound;
    }

    @Transactional
    @Override
    public boolean save(LampInbound inbound) {
        super.save(inbound);
        LampServer server = serverService.getById(inbound.getServerId());
        List<LampServiceMonth> serviceMonthList = serviceMonthService.list(new Date());
        XuiInbound xuiInbound = XuiInboundBuilder.build(inbound, serviceMonthList);
        XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
        xuiInbound = xServer.inboundAdd(xuiInbound);
        inbound.setXuiInbound(xuiInbound);
        super.updateById(inbound);
        return true;
    }

    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        LampInbound inbound = getById(id);
        LampServer server = serverService.getById(inbound.getServerId());
        XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
        xServer.inboundDelete(inbound.getXuiId());
        super.removeById(id);
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
