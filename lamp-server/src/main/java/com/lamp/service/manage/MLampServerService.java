package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.*;
import com.lamp.im.dto.SysTelegramSendDTO;
import com.lamp.im.service.ISysTelegramService;
import com.lamp.mapper.LampServerMapper;
import com.lamp.infrastructure.xui.XServer;
import com.lamp.infrastructure.xui.builder.XuiInboundBuilder;
import com.lamp.infrastructure.xui.entity.XuiClientTraffic;
import com.lamp.infrastructure.xui.entity.XuiInbound;
import com.lamp.infrastructure.xui.entity.XuiVmessSetting;
import com.lamp.infrastructure.xui.entity.XuiVmessSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
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

    @Autowired
    private ISysTelegramService sysTelegramService;

    @Override
    public LampServer getById(Serializable id) {
        LampServer server = super.getById(id);
        inboundService.setInboundList(server);
        return server;
    }

    @Override
    public List<LampServer> list() {
        LambdaQueryWrapper<LampServer> queryWrapper = new LambdaQueryWrapper<>(LampServer.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return baseMapper.selectList(queryWrapper);
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
                        SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
                        sysTelegramSendDTO.setTemplateName("add_client");
                        sysTelegramSendDTO.setTemplateType("telegram");
                        Map<String, Object> replaceMap = new HashMap<>();
                        replaceMap.put("member", member.getEmail());
                        replaceMap.put("inbound", server.getApiIp() + ":" + server.getRegion() + ":" + server.getRemark());
                        sysTelegramSendDTO.setReplaceMap(replaceMap);
                        sysTelegramService.send(sysTelegramSendDTO);
                    }
                }

                if (Objects.isNull(memberParam) || Objects.isNull(memberParam.getId())) {
                    // 删除节点
                    XuiVmessSettings xuiVmessSettings = (XuiVmessSettings) xuiInbound.getSettingsObject();
                    for (XuiVmessSetting xuiVmessSetting : xuiVmessSettings.getClients()) {
                        if (!validMemberIdSet.contains(Long.valueOf(xuiVmessSetting.getEmail().split("_")[3]))) {
                            xServer.deleteClient(xuiInbound.getId(), xuiVmessSetting.getId());
                            LampMember member = memberService.getById(Long.valueOf(xuiVmessSetting.getEmail().split("_")[3]));
                            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
                            sysTelegramSendDTO.setTemplateName("del_client");
                            sysTelegramSendDTO.setTemplateType("telegram");
                            Map<String, Object> replaceMap = new HashMap<>();
                            replaceMap.put("member", member.getEmail());
                            replaceMap.put("inbound", server.getApiIp() + ":" + server.getRegion() + ":" + server.getRemark());
                            sysTelegramSendDTO.setReplaceMap(replaceMap);
                            sysTelegramService.send(sysTelegramSendDTO);
                        }
                    }
                }
            }
        }
    }

    public void expirationReminder() {
        List<LampServer> serverList = list();
        for (LampServer server : serverList) {
            if (server.getExpiryDate().minusMonths(1).isBefore(LocalDate.now())) {
                SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
                sysTelegramSendDTO.setTemplateName("server_expiration_reminder");
                sysTelegramSendDTO.setTemplateType("telegram");
                Map<String, Object> replaceMap = new HashMap<>();
                replaceMap.put("remark", server.getRemark());
                replaceMap.put("region", server.getRegion());
                replaceMap.put("ip", server.getApiIp());
                replaceMap.put("expiryDate", server.getExpiryDate());
                sysTelegramSendDTO.setReplaceMap(replaceMap);
                sysTelegramService.send(sysTelegramSendDTO);
            }
        }
    }
}
