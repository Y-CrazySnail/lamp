package com.yeem.lamp.application.service.web;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServerDTO;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.web.ServerWebDomainService;
import com.yeem.lamp.domain.service.web.ServiceWebDomainService;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XClientStat;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServerWebAppService {

    @Autowired
    private ServerWebDomainService serverDomainService;
    @Autowired
    private ServiceWebDomainService serviceDomainService;

    public ServerDTO getById(Long id) {
        Server server = serverDomainService.getById(id);
        return new ServerDTO(server);
    }

    public List<ServerDTO> list() {
        List<Server> serverList = serverDomainService.list();
        return serverList.stream().map(ServerDTO::new).collect(Collectors.toList());
    }

    public IPage<ServerDTO> pages(int current, int size) {
        IPage<Server> page = serverDomainService.pages(current, size);
        IPage<ServerDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(ServerDTO::new).collect(Collectors.toList()));
        return pageDTO;
    }

    public void save(ServerDTO serverDTO) {
        Server server = serverDTO.convertServer();
        serverDomainService.save(server);
    }

    public void updateById(ServerDTO serverDTO) {
        Server server = serverDTO.convertServer();
        serverDomainService.updateById(server);
    }

    public void removeById(Long id) {
        serverDomainService.removeById(id);
    }

    public void syncRemoteServer(Long serverId) {
        serviceDomainService.generateServiceMonth();
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Services> servicesList = serviceDomainService.listService();
        servicesList.forEach(services -> serviceDomainService.setServiceMonth(services, current));
        Map<Long, String> servicesMap = servicesList.stream()
                .peek(services -> serviceDomainService.setServiceMonth(services, current))
                .filter(services -> services.isValid()
                        && null != services.getCurrentServiceMonth()
                        && services.getCurrentServiceMonth().isValid())
                .collect(Collectors.toMap(Services::getId, Services::getUuid));
        List<Server> serverList = new ArrayList<>();
        if (null == serverId) {
            serverList = serverDomainService.list();
        } else {
            Server server = serverDomainService.getById(serverId);
            serverList.add(server);
        }
        for (Server server : serverList) {
            try {
                XUIClient xuiClient = XUIClient.init(server);
                xuiClient.delInbound();
                xuiClient.addVmessInbound(serverId, server.getInboundPort(), servicesMap);
            } catch (Exception e) {
                log.error("服务器：{}-{}异常：", server.getApiIp(), server.getRegion(), e);
            }
        }
    }

    public void syncRemoteService(Long serviceId) {
        serviceDomainService.generateServiceMonth();
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Services> servicesList = new ArrayList<>();
        if (serviceId != null) {
            Services services = serviceDomainService.getById(serviceId);
            serviceDomainService.setServiceMonth(services, current);
            servicesList.add(services);
        } else {
            servicesList = serviceDomainService.listService();
            servicesList.forEach(services -> serviceDomainService.setServiceMonth(services, current));
        }
        List<Server> serverList = serverDomainService.list();
        for (Server server : serverList) {
            try {
                XUIClient xuiClient = XUIClient.init(server);
                XInbound xInbound = xuiClient.getInbound();
                for (Services services : servicesList) {
                    if (services.isValid() && null != services.getCurrentServiceMonth() && services.getCurrentServiceMonth().isValid()) {
                        boolean exist = false;
                        for (XClientStat clientStat : xInbound.getClientStats()) {
                            if (clientStat.getEmail().equals(String.valueOf(services.getId()))) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            log.info("add vmess client service id:{}, server id:{}", services.getId(), server.getId());
                            xuiClient.addVmessClient(xInbound, services.getUuid(), services.getId());
                        }
                    } else {
                        for (XClientStat clientStat : xInbound.getClientStats()) {
                            if (clientStat.getEmail().equals(String.valueOf(services.getId()))) {
                                log.info("delete vmess client service id:{}, uuid:{}", services.getId(), services.getUuid());
                                xuiClient.delVmessClient(xInbound.getId(), services.getUuid());
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("服务器：{}-{}异常：", server.getApiIp(), server.getRegion(), e);
            }
        }
    }
}
