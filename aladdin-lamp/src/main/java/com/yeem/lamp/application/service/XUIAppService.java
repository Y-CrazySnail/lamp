package com.yeem.lamp.application.service;

import cn.hutool.core.date.DateUtil;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.Server;
import com.yeem.lamp.domain.service.NodeVmessDomainService;
import com.yeem.lamp.domain.service.ServerDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XClientStat;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XUIAppService {

    @Autowired
    private ServerDomainService serverDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;
    @Autowired
    private NodeVmessDomainService nodeVmessDomainService;

    public void reset() {
        Date currentDate = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Server> serverList = serverDomainService.list();
        for (Server server : serverList) {
            try {
                List<NodeVmess> nodeVmessList = nodeVmessDomainService.listValidByServerId(server.getId(), currentDate);
                XUIClient xuiClient = XUIClient.init(server);
                xuiClient.delInbound();
                xuiClient.addVmessInbound(server, nodeVmessList);
            } catch (Exception e) {
                log.error("reset server:{} remote node error", server.getPostscript(), e);
            }
        }
    }

    public void syncRemoteDataTraffic() {
        Date currentDate = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Server> serverList = serverDomainService.list();
        for (Server server : serverList) {
            try {
                List<NodeVmess> nodeVmessList = nodeVmessDomainService.listValidByServerId(server.getId(), currentDate);
                Map<Long, NodeVmess> nodeVmessMap = nodeVmessList
                        .stream()
                        .collect(Collectors.toMap(NodeVmess::getId, nodeVmess -> nodeVmess));
                XUIClient xuiClient = XUIClient.init(server);
                List<XInbound> xInboundList = xuiClient.getInboundList();
                if (xInboundList.isEmpty()) {
                    continue;
                }
                for (XInbound xInbound : xInboundList) {
                    List<XClientStat> xClientStatList = xInbound.getClientStats();
                    Map<Long, XClientStat> xClientStatMap = xClientStatList.stream()
                            .collect(Collectors.toMap(xClientStat -> Long.valueOf(xClientStat.getEmail().split("_")[3]), xClientStat -> xClientStat));
                    for (XClientStat xClientStat : xClientStatList) {
                        Long nodeVmessId = Long.valueOf(xClientStat.getEmail().split("_")[3]);
                        if (nodeVmessMap.containsKey(nodeVmessId)) {
                            NodeVmess nodeVmess = nodeVmessMap.get(nodeVmessId);
                            nodeVmess.setServiceUp(xClientStat.getUp());
                            nodeVmess.setServiceDown(xClientStat.getDown());
                            nodeVmessDomainService.updateById(nodeVmess);
                        } else {
                            // 删除远端节点
                            log.info("delete vmess client email:{}", xClientStat.getEmail());
                            xuiClient.delVmessClient(xClientStat.getInboundId(), xClientStat.getId());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("sync server:{} remote node error", server.getPostscript(), e);
            }
        }
    }

    public void syncRemoteNode() {
        Date currentDate = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Server> serverList = serverDomainService.list();
        for (Server server : serverList) {
            try {
                List<NodeVmess> nodeVmessList = nodeVmessDomainService.listValidByServerId(server.getId(), currentDate);
                XUIClient xuiClient = XUIClient.init(server);
                List<XInbound> xInboundList = xuiClient.getInboundList();
                if (xInboundList.isEmpty()) {
                    continue;
                }
                for (XInbound xInbound : xInboundList) {
                    List<XClientStat> xClientStatList = xInbound.getClientStats();
                    Map<Long, XClientStat> xClientStatMap = xClientStatList.stream()
                            .collect(Collectors.toMap(xClientStat -> Long.valueOf(xClientStat.getEmail().split("_")[3]), xClientStat -> xClientStat));
                    for (NodeVmess nodeVmess : nodeVmessList) {
                        if (!xClientStatMap.containsKey(nodeVmess.getId())) {
                            log.info("add vmess client service id:{}, server id:{}", nodeVmess.getServiceId(), nodeVmess.getServerId());
                            xuiClient.addVmessClient(xInbound, nodeVmess);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("sync server:{} remote node error", server.getPostscript(), e);
            }
        }
    }
}
