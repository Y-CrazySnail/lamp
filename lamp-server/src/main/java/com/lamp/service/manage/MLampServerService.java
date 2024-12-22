package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.*;
import com.lamp.mapper.LampServerMapper;
import com.lamp.xui.XServer;
import com.lamp.xui.builder.XuiInboundBuilder;
import com.lamp.xui.entity.XuiClientTraffic;
import com.lamp.xui.entity.XuiInbound;
import com.lamp.xui.entity.XuiVmessSetting;
import com.lamp.xui.entity.XuiVmessSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MLampServerService extends ServiceImpl<LampServerMapper, LampServer> {

    @Autowired
    private MLampMemberService memberService;

    @Autowired
    private MLampInboundService inboundService;

    @Autowired
    private MLampClientTrafficService clientTrafficService;

    @Override
    public LampServer getById(Serializable id) {
        LampServer server = super.getById(id);
        inboundService.setInboundList(server);
        return server;
    }

    @Override
    public boolean save(LampServer entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(LampServer entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        LampServer server = getById(id);
        for (LampInbound inbound : server.getInboundList()) {
            inboundService.removeById(inbound.getId());
        }
        return super.removeById(id);
    }

    @Transactional
    public void sync(LampServer serverParam, LampMember memberParam) {
        // 查询需要同步的服务器信息，为空时同步全部服务器
        LambdaQueryWrapper<LampServer> queryWrapper = new LambdaQueryWrapper<>(LampServer.class);
        if (Objects.nonNull(serverParam) && Objects.nonNull(serverParam.getId())) {
            queryWrapper.eq(LampServer::getId, serverParam.getId());
        }
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampServer> serverList = baseMapper.selectList(queryWrapper);

        // 查询需要同步的会员服务信息
        List<LampMember> memberList = new ArrayList<>();
        if (Objects.nonNull(memberParam) && Objects.nonNull(memberParam.getId())) {
            memberList.add(memberService.getById(memberParam.getId()));
        } else {
            memberList = memberService.list();
        }
        if (memberList.isEmpty()) {
            return;
        }
        List<LampMember> validMemberList = memberList.stream().filter(LampMember::isValid).collect(Collectors.toList());
        Set<Long> validMemberIdSet = validMemberList.stream().map(LampMember::getId).collect(Collectors.toSet());

        // 开始同步流量 远程->本地
        for (LampServer server : serverList) {
            XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
            List<XuiInbound> xuiInboundList = xServer.inboundList();
            for (XuiInbound xuiInbound : xuiInboundList) {
                List<XuiClientTraffic> xuiClientTrafficList = xuiInbound.getClientStats();
                for (XuiClientTraffic xuiClientTraffic : xuiClientTrafficList) {
                    String[] param = xuiClientTraffic.getEmail().split("_");
                    if (param.length != 4) {
                        continue;
                    }
                    LampClientTraffic clientTraffic = LampClientTraffic.convert(xuiClientTraffic);
                    clientTrafficService.saveOrUpdate(clientTraffic);
                }
            }
        }

        // 开始同步节点 本地->远程
        for (LampServer server : serverList) {
            inboundService.setInboundList(server);
            XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
            List<XuiInbound> xuiInboundList = xServer.inboundList();
            for (XuiInbound xuiInbound : xuiInboundList) {
                LampInbound inbound = null;
                for (LampInbound i : server.getInboundList()) {
                    if (i.getXuiId().equals(xuiInbound.getId())) {
                        inbound = i;
                        break;
                    }
                }
                if (Objects.isNull(inbound)) {
                    log.warn("本地无该入站配置，忽略不处理：{}", xuiInbound);
                    continue;
                }

                // 新增节点
                Set<Long> remoteServiceMonthIdSet = xuiInbound.getClientStats().stream()
                        .map(x -> Long.valueOf(x.getEmail().split("_")[3]))
                        .collect(Collectors.toSet());
                for (LampMember member : validMemberList) {
                    if (!remoteServiceMonthIdSet.contains(member.getId())) {
                        XuiInbound x = XuiInboundBuilder.build(inbound, member);
                        xServer.addClient(x);
                    }
                }

                if (Objects.isNull(memberParam) || Objects.isNull(memberParam.getId())) {
                    // 删除节点
                    XuiVmessSettings xuiVmessSettings = (XuiVmessSettings) xuiInbound.getSettingsObject();
                    for (XuiVmessSetting xuiVmessSetting : xuiVmessSettings.getClients()) {
                        if (!validMemberIdSet.contains(Long.valueOf(xuiVmessSetting.getEmail().split("_")[3]))) {
                            xServer.deleteClient(xuiInbound.getId(), xuiVmessSetting.getId());
                        }
                    }
                }
            }
        }
    }
}
