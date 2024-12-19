package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServer;
import com.lamp.mapper.LampInboundMapper;
import com.lamp.xui.XServer;
import com.lamp.xui.entity.XuiInbound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
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

    @Override
    public LampInbound getById(Serializable id) {
        LampInbound inbound = getById(id);
        LampServer server = serverService.getById(inbound.getServerId());
        XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
        List<XuiInbound> xuiInboundList = xServer.inboundList(inbound.getXuiId());
        if (xuiInboundList.isEmpty()) {
            return null;
        }
        return inbound;
    }

    @Override
    public boolean save(LampInbound inbound) {
        super.save(inbound);
        LampServer server = serverService.getById(inbound.getServerId());
        XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
//        xServer.inboundAdd();
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
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
