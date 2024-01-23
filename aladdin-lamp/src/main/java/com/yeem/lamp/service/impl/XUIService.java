package com.yeem.lamp.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
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
        List<AladdinServer> serverList = aladdinServerService.list();
        List<AladdinService> aladdinServiceList = aladdinServiceService.list();
        try {
            for (AladdinServer server : serverList) {
                Long serverId = server.getId();
                List<XUIVmessClient> vmessClientList = new ArrayList<>();
                for (AladdinService aladdinService : aladdinServiceList) {
                    AladdinMember aladdinMember = aladdinMemberService.getById(aladdinService.getMemberId());
                    XUIVmessClient xuiVmessClient = new XUIVmessClient(aladdinService.getUuid(),
                            Base64.encode(serverId + "_" + aladdinService.getId()).replace("=", ""),
                            0,
                            0,
                            true,
                            "",
                            Base64.encode(String.valueOf(aladdinService.getId())).replace("=", "") + serverId,
                            0
                    );
                    vmessClientList.add(xuiVmessClient);
                }
                int year = DateUtil.year(new Date());
                int month = DateUtil.month(new Date()) + 1;
                List<AladdinNodeVmess> nodeVmessList = aladdinNodeVmessService.listByServerId(serverId, year, month);
                String apiIp = server.getApiIp();
                int apiPort = server.getApiPort();
                String apiUsername = server.getApiUsername();
                String apiPassword = server.getApiPassword();
                log.info("开始同步{}服务器远端节点信息----------", server.getPostscript());
                XUIInboundData xuiInboundData = XUIRequest.request()
                        .ip(apiIp).port(apiPort)
                        .nodeRemark(server.getNodeRemark()).nodePort(server.getNodePort())
                        .login(apiUsername, apiPassword)
                        .syncNode(vmessClientList);
                log.info("结束同步{}服务器远端节点信息----------", server.getPostscript());
                log.info("开始同步{}服务器本地节点信息----------", server.getPostscript());
                for (XUIInboundData.ClientStats clientStat : xuiInboundData.getClientStats()) {
                    Long serviceId = Long.valueOf(Base64.decodeStr(clientStat.getEmail()).split("_")[1]);
                    List<AladdinService> serviceList = aladdinServiceList.stream().filter(e -> e.getId().equals(serviceId)).collect(Collectors.toList());
                    if (serviceList.isEmpty()) {
                        log.error("service未查询到：{}", serviceId);
                        continue;
                    }
                    AladdinService service = serviceList.get(0);
                    List<AladdinNodeVmess> nodeList = nodeVmessList
                            .stream().filter(e ->
                                    e.getServiceId().equals(serviceId)
                                            && e.getNodeId().equals(service.getUuid())
                            )
                            .collect(Collectors.toList());
                    if (nodeList.isEmpty()) {
                        // 不存在用户当前UUID的节点，做新增节点操作
                        log.info("创建本地节点流量操作：serviceId:{}, serverId:{}, uuid:{}, year：{}, month：{}",
                                service.getId(), serverId, service.getUuid(), year, month);
                        AladdinNodeVmess addNodeVmess = new AladdinNodeVmess();
                        addNodeVmess.setNodeType(Constant.NODE_TYPE_PRIVATE);
                        addNodeVmess.setNodePs(server.getSubscribeNamePrefix());
                        addNodeVmess.setNodeAdd(server.getSubscribeIp());
                        addNodeVmess.setNodePort(String.valueOf(server.getSubscribePort()));
                        addNodeVmess.setNodeId(service.getUuid());
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
                aladdinNodeVmessService.updateByServerId(serverId, null, server.getSubscribeNamePrefix(), server.getSort());
                log.info("结束同步{}服务器本地节点信息----------", server.getPostscript());
            }
            aladdinServiceService.refreshStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
