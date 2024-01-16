package com.yeem.lamp.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.entity.AladdinNodeVmess;
import com.yeem.lamp.entity.AladdinServer;
import com.yeem.lamp.entity.AladdinService;
import com.yeem.lamp.service.IAladdinMemberService;
import com.yeem.lamp.service.IAladdinNodeVmessService;
import com.yeem.lamp.service.IAladdinServerService;
import com.yeem.lamp.service.IAladdinServiceService;
import com.yeem.lamp.xui.XUIInboundData;
import com.yeem.lamp.xui.XUIRequest;
import com.yeem.lamp.xui.XUIVmessClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XUIService {

    @Autowired
    private IAladdinServerService aladdinServerService;
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private IAladdinMemberService aladdinMemberService;
    @Autowired
    private IAladdinNodeVmessService aladdinNodeVmessService;

    public void sync() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AladdinMember> memberList = aladdinMemberService.list();
        List<AladdinServer> serverList = aladdinServerService.list();
        List<AladdinService> aladdinServiceList = aladdinServiceService.list();
        List<XUIVmessClient> vmessClientList = new ArrayList<>();
        for (AladdinService aladdinService : aladdinServiceList) {
            AladdinMember aladdinMember = aladdinMemberService.getById(aladdinService.getMemberId());
            XUIVmessClient xuiVmessClient = new XUIVmessClient(aladdinMember.getUuid(),
                    Base64.encode(String.valueOf(aladdinService.getId())).replace("=", ""),
                    0,
                    0,
                    true,
                    "",
                    Base64.encode(String.valueOf(aladdinService.getId())).replace("=", "") + aladdinService.getId(),
                    0
            );
            vmessClientList.add(xuiVmessClient);
        }
        try {
            for (AladdinServer server : serverList) {
                Long serverId = server.getId();
                int year = DateUtil.year(new Date());
                int month = DateUtil.month(new Date()) + 1;
                List<AladdinNodeVmess> nodeVmessList = aladdinNodeVmessService.listByServerId(serverId, year, month);
                String apiIp = server.getApiIp();
                int apiPort = server.getApiPort();
                String apiRemark = server.getApiRemark();
                String apiUsername = server.getApiUsername();
                String apiPassword = server.getApiPassword();
                XUIInboundData xuiInboundData = XUIRequest.request()
                        .ip(apiIp).port(apiPort).remark(apiRemark).login(apiUsername, apiPassword)
                        .sync(vmessClientList);
                log.info("入站列表数据：{}", xuiInboundData);
                for (XUIInboundData.ClientStats clientStat : xuiInboundData.getClientStats()) {
                    Long serviceId = Long.valueOf(Base64.decodeStr(clientStat.getEmail()));
                    List<AladdinService> serviceList = aladdinServiceList.stream().filter(e -> e.getId().equals(serviceId)).collect(Collectors.toList());
                    if (serviceList.isEmpty()) {
                        log.error("service未查询到：{}", serviceId);
                        continue;
                    }
                    AladdinService service = serviceList.get(0);
                    AladdinMember member = memberList
                            .stream().filter(e -> e.getId().equals(service.getMemberId())).collect(Collectors.toList()).get(0);
                    List<AladdinNodeVmess> nodeList = nodeVmessList
                            .stream().filter(e -> e.getServiceId().equals(serviceId) && e.getNodeId().equals(member.getUuid()))
                            .collect(Collectors.toList());
                    if (nodeList.isEmpty()) {
                        // 不存在用户当前UUID的节点，做新增节点操作
                        log.info("创建本地节点流量操作：memberId:{}, serviceId:{}, uuid:{}, year：{}, month：{}",
                                member.getId(), serverId, member.getUuid(), year, month);
                        AladdinNodeVmess addNodeVmess = new AladdinNodeVmess();
                        addNodeVmess.setNodeType(Constant.NODE_TYPE_PRIVATE);
                        addNodeVmess.setNodePs(server.getSubscribeNamePrefix());
                        addNodeVmess.setNodeAdd(server.getSubscribeIp());
                        addNodeVmess.setNodePort(String.valueOf(server.getSubscribePort()));
                        addNodeVmess.setNodeId(member.getUuid());
                        addNodeVmess.setAid("0");
                        addNodeVmess.setNet("tcp");
                        addNodeVmess.setType("none");
                        addNodeVmess.setTls("none");
                        addNodeVmess.setServerId(serverId);
                        addNodeVmess.setServiceId(serviceId);
                        addNodeVmess.setServiceYear(year);
                        addNodeVmess.setServiceMonth(month);
                        addNodeVmess.setServiceUp(0L);
                        addNodeVmess.setServiceDown(0L);
                        aladdinNodeVmessService.save(addNodeVmess);
                    } else {
                        for (AladdinNodeVmess nodeVmess : nodeList) {
                            // 存在用户当前UUID的节点，做更新节点流量操作
                            log.info("更新节点流量操作：memberId:{}, serviceId:{}, nodeVmessId:{}, uuid:{}, year：{}, month：{}",
                                    member.getId(), serverId, nodeVmess.getId(), member.getUuid(), year, month);
                            nodeVmess.setServiceUp(clientStat.getUp());
                            nodeVmess.setServiceDown(clientStat.getDown());
                            aladdinNodeVmessService.updateById(nodeVmess);
                            nodeVmessList.removeIf(e -> nodeVmess.getId().equals(e.getId()));
                        }
                    }
                }
                for (AladdinNodeVmess nodeVmess : nodeVmessList) {
                    nodeVmess.setNodeType(Constant.NODE_TYPE_EXPIRED);
                    aladdinNodeVmessService.updateById(nodeVmess);
                }
                aladdinNodeVmessService.updateByServerId(serverId, null, server.getSubscribeNamePrefix());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
