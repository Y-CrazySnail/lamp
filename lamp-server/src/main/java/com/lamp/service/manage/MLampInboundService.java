package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampServer;
import com.lamp.mapper.LampInboundMapper;
import com.lamp.infrastructure.xui.XServer;
import com.lamp.infrastructure.xui.builder.XuiInboundBuilder;
import com.lamp.infrastructure.xui.entity.XuiInbound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MLampInboundService extends ServiceImpl<LampInboundMapper, LampInbound> {

    @Autowired
    private LampInboundMapper inboundMapper;

    @Autowired
    private MLampServerService serverService;

    @Autowired
    private MLampMemberService memberService;

    @Autowired
    private MLampSubscriptionService subscriptionService;

    @Autowired
    private MLampClientTrafficService clientTrafficService;

    @Override
    public LampInbound getById(Serializable id) {
        return inboundMapper.selectById(id);
    }

    @Transactional
    @Override
    public boolean save(LampInbound inbound) {
        super.save(inbound);
        LampServer server = serverService.getById(inbound.getServerId());
        List<LampMember> memberList = memberService.list().stream().filter(LampMember::isValid).collect(Collectors.toList());
        XuiInbound xuiInbound = XuiInboundBuilder.build(inbound, memberList);
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
