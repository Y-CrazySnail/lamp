package com.yeem.lamp.domain.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.Server;
import com.yeem.lamp.domain.repository.ServiceRepository;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XClientStat;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceDomainService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Services> listByMemberId(Long memberId) {
        List<Services> servicesList = serviceRepository.listByMemberId(memberId);
        servicesList.forEach(Services::dealSurplus);
        return servicesList;
    }

    public List<Server> listServer() {
        return serviceRepository.listServer();
    }

    public Services getById(Long id) {
        return serviceRepository.getServiceById(id);
    }

    public Services getByUUID(String uuid) {
        Services services = serviceRepository.getByUUID(uuid);
        services.dealSurplus();
        return services;
    }

    public IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email) {
        return serviceRepository.pages(current, size, memberId, status, wechat, email);
    }

    public void updateById(Services services) {
        serviceRepository.updateById(services);
    }

    public void save(Services services) {
        serviceRepository.save(services);
    }

    public void removeById(Long id) {
        serviceRepository.removeById(id);
    }

    public void updateUUID(Long memberId, Long serviceId, String uuid) {
        serviceRepository.updateUUID(memberId, serviceId, uuid);
    }

    /**
     * 同步远程流量至本地
     */
    public void syncDataTraffic() {
        Date currentDate = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Server> serverList = serviceRepository.listServer();
        List<Services> servicesList = serviceRepository.listService();
        Map<Long, Services> servicesMap = servicesList.stream()
                .peek(services -> {
                    services.setServiceTodayUp(0L);
                    services.setServiceTodayDown(0L);
                })
                .collect(Collectors.toMap(Services::getId, services -> services));
        List<NodeVmess> nodeVmessList = serviceRepository.listNodeVmess(currentDate);
        Map<String, NodeVmess> nodeVmessMap = nodeVmessList.stream()
                .collect(Collectors.toMap(NodeVmess::key, nodeVmess -> nodeVmess));
        for (Server server : serverList) {
            try {
                for (Services services : servicesList) {
                    String nodeVmessKey = services.getId() + "_" + server.getId();
                    if (!services.isValid()) {
                        nodeVmessMap.remove(nodeVmessKey);
                    } else {
                        if (!nodeVmessMap.containsKey(nodeVmessKey)) {
                            NodeVmess nodeVmess = NodeVmess.init(services.getId(), server.getId(), currentDate);
                            nodeVmessMap.put(nodeVmess.key(), nodeVmess);
                        }
                    }
                }
                XUIClient xuiClient = XUIClient.init(server);
                List<XInbound> xInboundList = xuiClient.getInboundList();
                if (xInboundList.isEmpty()) {
                    continue;
                }
                for (XInbound xInbound : xInboundList) {
                    List<XClientStat> xClientStatList = xInbound.getClientStats();
                    for (XClientStat xClientStat : xClientStatList) {
                        String key = xClientStat.getEmail();
                        if (nodeVmessMap.containsKey(key)) {
                            nodeVmessMap.get(key).setServiceUp(xClientStat.getUp());
                            nodeVmessMap.get(key).setServiceDown(xClientStat.getDown());
                        } else {
                            log.info("delete vmess client email:{}", xClientStat.getEmail());
                            xuiClient.delVmessClient(xClientStat.getInboundId(), xClientStat.getId());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("deal server:{} error", server.getId(), e);
            }
        }
        Set<Long> invalidServiceIdSet = new HashSet<>();
        nodeVmessMap.forEach((nodeKey, nodeVmess) -> {
            Long serviceId = nodeVmess.getServiceId();
            if (servicesMap.containsKey(serviceId)) {
                Services services = servicesMap.get(serviceId);
                services.setServiceTodayUp(services.getServiceTodayUp() + nodeVmess.getServiceUp());
                services.setServiceTodayDown(services.getServiceTodayDown() + nodeVmess.getServiceDown());
                services.setServiceUp(services.getServiceArchiveUp() + services.getServiceTodayUp());
                services.setServiceDown(services.getServiceArchiveDown() + services.getServiceTodayDown());
                if (!services.isValid()) {
                    invalidServiceIdSet.add(services.getId());
                }
            }
            serviceRepository.saveNodeVmess(nodeVmess);
        });
        for (Server server : serverList) {
            XUIClient xuiClient = XUIClient.init(server);
            List<XInbound> xInboundList = xuiClient.getInboundList();
            if (xInboundList.isEmpty()) {
                continue;
            }
            for (XInbound xInbound : xInboundList) {
                List<XClientStat> xClientStatList = xInbound.getClientStats();
                for (XClientStat xClientStat : xClientStatList) {
                    Long serviceId = Long.valueOf(xClientStat.getEmail().split("_")[0]);
                    if (invalidServiceIdSet.contains(serviceId)) {
                        log.info("delete vmess client email:{}", xClientStat.getEmail());
                        xuiClient.delVmessClient(xClientStat.getInboundId(), xClientStat.getId());
                    }
                }
            }
        }
        servicesMap.forEach((serviceKey, services) -> serviceRepository.updateService(services));
    }

    /**
     * 重重所有客户端流量
     */
    public void resetClientTraffic() {
        List<Server> serverList = serviceRepository.listServer();
        for (Server server : serverList) {
            XUIClient xuiClient = XUIClient.init(server);
            List<XInbound> xInboundList = xuiClient.getInboundList();
            for (XInbound xInbound : xInboundList) {
                xuiClient.resetClientTraffic(xInbound.getId());
            }
        }
    }

    public void syncRemoteServer(Long serverId) {
        List<Services> servicesList = serviceRepository.listService();
        Map<Long, String> servicesMap = servicesList.stream()
                .filter(Services::isValid)
                .collect(Collectors.toMap(Services::getId, Services::getUuid));
        Server server = serviceRepository.getServerById(serverId);
        XUIClient xuiClient = XUIClient.init(server);
        xuiClient.delInbound();
        xuiClient.addVmessInbound(serverId, server.getNodePort(), servicesMap);
    }

    public void syncRemoteService(Long serviceId) {
        Services services = serviceRepository.getServiceById(serviceId);
        List<Server> serverList = serviceRepository.listServer();
        for (Server server : serverList) {
            XUIClient xuiClient = XUIClient.init(server);
            List<XInbound> xInboundList = xuiClient.getInboundList();
            for (XInbound xInbound : xInboundList) {
                boolean exist = false;
                for (XClientStat clientStat : xInbound.getClientStats()) {
                    if (serviceId.equals(Long.valueOf(clientStat.getEmail().split("_")[0]))) {
                        exist = true;
                    }
                }
                if (!exist) {
                    log.info("add vmess client service id:{}, server id:{}", serviceId, server.getId());
                    xuiClient.addVmessClient(xInbound, services.getUuid(), serviceId, server.getId());
                }
            }
        }
    }

    public List<NodeVmess> listNodeVmess(String uuid) {
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        List<Server> serverList = serviceRepository.listServer();
        for (Server server : serverList) {
            NodeVmess nodeVmess = NodeVmess.init(uuid,
                    server.getSubscribeNamePrefix(),
                    server.getSubscribeIp(),
                    server.getSubscribePort()
            );
            nodeVmessList.add(nodeVmess);
        }
        return nodeVmessList;
    }
}
