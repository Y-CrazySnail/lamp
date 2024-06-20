package com.yeem.lamp.application.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServerDTO;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.ServerDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XClientStat;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServerAppService {

    @Autowired
    private ServerDomainService serverDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;

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
        Date current = DateUtil.beginOfDay(new Date());
        List<Services> servicesList = serviceDomainService.listService();
        servicesList.forEach(services -> serviceDomainService.setServiceMonth(services, current));
        Map<Long, String> servicesMap = servicesList.stream()
                .peek(services -> serviceDomainService.setServiceMonth(services, current))
                .filter(services -> services.isValid()
                        && null != services.getCurrentServiceMonth()
                        && services.getCurrentServiceMonth().isValid())
                .collect(Collectors.toMap(Services::getId, Services::getUuid));
        Server server = serverDomainService.getById(serverId);
        XUIClient xuiClient = XUIClient.init(server);
        xuiClient.delInbound();
        xuiClient.addVmessInbound(serverId, server.getInboundPort(), servicesMap);
    }

    public void syncRemoteService(Long serviceId) {
        serviceDomainService.generateServiceMonth();
        Date current = DateUtil.beginOfDay(new Date());
        Services services = serviceDomainService.getById(serviceId);
        serviceDomainService.setServiceMonth(services, current);
        if (services.isValid() && services.getCurrentServiceMonth() != null && services.getCurrentServiceMonth().isValid()) {
            List<Server> serverList = serverDomainService.list();
            for (Server server : serverList) {
                XUIClient xuiClient = XUIClient.init(server);
                XInbound xInbound = xuiClient.getInbound();
                boolean exist = false;
                for (XClientStat clientStat : xInbound.getClientStats()) {
                    if (serviceId.equals(Long.valueOf(clientStat.getEmail()))) {
                        exist = true;
                    }
                }
                if (!exist) {
                    log.info("add vmess client service id:{}, server id:{}", serviceId, server.getId());
                    xuiClient.addVmessClient(xInbound, services.getUuid(), serviceId, server.getId());
                }
            }
        } else {
            List<Server> serverList = serverDomainService.list();
            for (Server server : serverList) {
                XUIClient xuiClient = XUIClient.init(server);
                XInbound xInbound = xuiClient.getInbound();
                for (XClientStat clientStat : xInbound.getClientStats()) {
                    if (serviceId.equals(Long.valueOf(clientStat.getEmail()))) {
                        log.info("delete vmess client service id:{}, server id:{}", serviceId, server.getId());
                        xuiClient.delVmessClient(xInbound.getId(), services.getUuid());
                    }
                }
            }
        }
    }
}
