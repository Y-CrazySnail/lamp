package com.yeem.lamp.infrastructure.persistence.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinNodeVmess;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinServer;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinMemberService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinNodeVmessService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServerService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServiceService;
import com.yeem.lamp.xui.XUIInboundData;
import com.yeem.lamp.xui.XUIRequest;
import com.yeem.lamp.xui.XUIVmessClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class XUIService {

    private static boolean STATUS = false;
    @Autowired
    private IAladdinServerService aladdinServerService;
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private IAladdinMemberService aladdinMemberService;
    @Autowired
    private IAladdinNodeVmessService aladdinNodeVmessService;

    public void sync() {
        try {
            if (!STATUS) {
                STATUS = true;
                List<AladdinServer> serverList = aladdinServerService.list();
                List<ServiceDo> serviceDoList = aladdinServiceService.listValid();
                aladdinNodeVmessService.updateByValidServiceList(serviceDoList);
                for (AladdinServer server : serverList) {
                    int year = DateUtil.year(new Date());
                    int month = DateUtil.month(new Date()) + 1;
                    for (ServiceDo service : serviceDoList) {
                        aladdinNodeVmessService.save(server, service, year, month);
                    }
                }
                for (AladdinServer server : serverList) {
                    Long serverId = server.getId();
                    int year = DateUtil.year(new Date());
                    int month = DateUtil.month(new Date()) + 1;
                    List<AladdinNodeVmess> nodeVmessList = aladdinNodeVmessService.listByServerId(serverId, year, month);
                    List<XUIVmessClient> vmessClientList = new ArrayList<>();
                    for (AladdinNodeVmess nodeVmess : nodeVmessList) {
                        XUIVmessClient xuiVmessClient = new XUIVmessClient(
                                nodeVmess.getNodeId(),
                                Base64.encode(String.valueOf(nodeVmess.getId())).replace("=", ""),
                                0,
                                0,
                                true,
                                "",
                                Base64.encode(String.valueOf(nodeVmess.getId())).replace("=", "") + serverId,
                                0
                        );
                        vmessClientList.add(xuiVmessClient);
                    }
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
                    log.info("开始同步{}服务器本地节点流量信息----------", server.getPostscript());
                    for (XUIInboundData.ClientStats clientStat : xuiInboundData.getClientStats()) {
                        Long nodeVmessId = Long.valueOf(Base64.decodeStr(clientStat.getEmail()));
                        LambdaUpdateWrapper<AladdinNodeVmess> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(AladdinNodeVmess::getId, nodeVmessId);
                        updateWrapper.set(AladdinNodeVmess::getServiceUp, clientStat.getUp());
                        updateWrapper.set(AladdinNodeVmess::getServiceDown, clientStat.getDown());
                        aladdinNodeVmessService.update(updateWrapper);
                    }
                    aladdinNodeVmessService.updateByServerId(serverId, null, server.getSubscribeNamePrefix(), server.getSort());
                    log.info("结束同步{}服务器本地节点流量信息----------", server.getPostscript());
                }
                aladdinServiceService.refreshStatus();
            }
        } catch (Exception e) {
            log.error("执行同步操作失败", e);
        } finally {
            STATUS = false;
        }
    }
}
