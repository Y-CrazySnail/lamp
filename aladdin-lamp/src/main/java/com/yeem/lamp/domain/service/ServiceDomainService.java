package com.yeem.lamp.domain.service;

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

    public List<Services> list() {
        return serviceRepository.listService();
    }

    public Services getById(Long id) {
        return serviceRepository.getById(id);
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
        services.calculateStatus();
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
        nodeVmessMap.forEach((nodeKey, nodeValue) -> {
            Long serviceId = nodeValue.getServiceId();
            if (servicesMap.containsKey(serviceId)) {
                Services services = servicesMap.get(serviceId);
                services.setServiceTodayUp(services.getServiceTodayUp() + nodeValue.getServiceUp());
                services.setServiceTodayDown(services.getServiceTodayDown() + nodeValue.getServiceDown());
                services.setServiceUp(services.getServiceArchiveUp() + services.getServiceTodayUp());
                services.setServiceDown(services.getServiceArchiveDown() + services.getServiceTodayDown());
                if (!services.isValid()) {
                    invalidServiceIdSet.add(services.getId());
                }
            }
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
        // todo 落库
    }
}
