package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.*;
import com.lamp.mapper.LampServerMapper;
import com.lamp.xui.XServer;
import com.lamp.xui.builder.XuiInboundBuilder;
import com.lamp.xui.entity.*;
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
    private MLampServiceMonthService serviceMonthService;

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
        return super.removeById(id);
    }

    @Transactional
    public void sync(LampServer serverParam, LampMember memberParam) {
        Date current = new Date();

        // 同步本地月服务
        serviceMonthService.sync(current);

        // 查询需要同步的服务器信息，为空时同步全部服务器
        LambdaQueryWrapper<LampServer> queryWrapper = new LambdaQueryWrapper<>(LampServer.class);
        if (Objects.nonNull(serverParam) && Objects.nonNull(serverParam.getId())) {
            queryWrapper.eq(LampServer::getId, serverParam.getId());
        }
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampServer> serverList = baseMapper.selectList(queryWrapper);

        // 查询需要同步的会员服务信息
        List<LampMember> memberList = memberService.list(memberParam, current);
        List<LampServiceMonth> serviceMonthList = new ArrayList<>();
        Set<Long> serviceMonthIdSet = new HashSet<>();
        for (LampMember member : memberList) {
            if (Objects.nonNull(member.getServiceList()) && !member.getServiceList().isEmpty()) {
                List<LampService> serviceList = member.getServiceList().stream().filter(LampService::isNotExpired).collect(Collectors.toList());
                for (LampService service : serviceList) {
                    if (Objects.nonNull(service.getServiceMonthList()) && !service.getServiceMonthList().isEmpty()) {
                        service.getServiceMonthList().forEach(LampServiceMonth::calculateClientTraffic);
                        List<LampServiceMonth> tempServiceMonthList = service.getServiceMonthList();
                        tempServiceMonthList = tempServiceMonthList.stream().filter(LampServiceMonth::isNotDeplete).collect(Collectors.toList());
                        serviceMonthList.addAll(tempServiceMonthList);
                        tempServiceMonthList.forEach(s -> serviceMonthIdSet.add(s.getId()));
                    }
                }
            }
        }

        // 开始同步流量 远程->本地
        for (LampServer server : serverList) {
            XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
            List<XuiInbound> xuiInboundList = xServer.inboundList(null);
            for (XuiInbound xuiInbound : xuiInboundList) {
                List<XuiClientTraffic> xuiClientTrafficList = xuiInbound.getClientStats();
                for (XuiClientTraffic xuiClientTraffic : xuiClientTrafficList) {
                    String[] param = xuiClientTraffic.getEmail().split("_");
                    if (param.length != 4) {
                        continue;
                    }
                    Long serviceMonthId = Long.valueOf(param[3]);
                    if (!serviceMonthIdSet.contains(serviceMonthId)) {
                        continue;
                    }
                    LampClientTraffic clientTraffic = LampClientTraffic.convert(xuiClientTraffic);
                    clientTrafficService.saveOrUpdate(clientTraffic);
                }
            }
        }

        // 开始同步节点（增量） 本地->远程
        for (LampServer server : serverList) {
            inboundService.setInboundList(server);
            XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
            List<XuiInbound> xuiInboundList = xServer.inboundList(null);
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
                List<XuiClientTraffic> xuiClientTrafficList = xuiInbound.getClientStats();
                Set<Long> remoteServiceMonthIdSet = xuiClientTrafficList.stream().map(x -> {
                    String[] param = x.getEmail().split("_");
                    return Long.valueOf(param[3]);
                }).collect(Collectors.toSet());
                for (LampServiceMonth serviceMonth : serviceMonthList) {
                    if (!remoteServiceMonthIdSet.contains(serviceMonth.getId())) {
                        XuiInbound x = XuiInboundBuilder.build(inbound, serviceMonth);
                        xServer.addClient(x);
                    }
                }
            }
        }

        // 对比本地与远程节点，删除无效节点
        if (Objects.isNull(memberParam) || Objects.isNull(memberParam.getId())) {
            for (LampServer server : serverList) {
                inboundService.setInboundList(server);
                XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
                List<XuiInbound> xuiInboundList = xServer.inboundList(null);
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
                    XuiVmessSettings xuiVmessSettings = (XuiVmessSettings) xuiInbound.getSettingsObject();
                    for (XuiVmessSetting xuiVmessSetting : xuiVmessSettings.getClients()) {
                        String[] param = xuiVmessSetting.getEmail().split("_");
                        Long serviceMonthId = Long.valueOf(param[3]);
                        if (!serviceMonthIdSet.contains(serviceMonthId)) {
                            // todo 发通知
                            xServer.deleteClient(xuiInbound.getId(), xuiVmessSetting.getId());
                        }
                    }
                }
            }
        }
    }
}
