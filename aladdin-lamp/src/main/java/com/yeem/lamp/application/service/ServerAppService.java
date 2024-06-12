package com.yeem.lamp.application.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServerDTO;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.Server;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.NodeVmessDomainService;
import com.yeem.lamp.domain.service.ServerDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerAppService {

    @Autowired
    private ServerDomainService serverDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;
    @Autowired
    private NodeVmessDomainService nodeVmessDomainService;

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

    public void syncNode() {
        Date currentDate = DateUtil.beginOfDay(new Date()).toJdkDate();
        nodeVmessDomainService.expired();
        List<Server> serverList = serverDomainService.list();
        List<Services> serviceList = serviceDomainService.list();
        for (Services services : serviceList) {
            // 可用服务
            if (services.getStatus().equals(Services.STATUS.VALID.getValue())) {
                for (Server server : serverList) {
                    List<NodeVmess> nodeVmessList = nodeVmessDomainService.list(server.getId(), services.getId(), currentDate);
                    for (NodeVmess nodeVmess : nodeVmessList) {
                        if (!nodeVmess.getNodeId().equals(services.getUuid())) {
                            nodeVmess.setNodeType("expired");
                            nodeVmessDomainService.updateById(nodeVmess);
                        }
                    }
                    int count = nodeVmessDomainService.count(server.getId(), services.getId(), services.getUuid(), currentDate);
                    if (count == 0) {
                        // 新增节点
                        NodeVmess nodeVmess = new NodeVmess();
                        nodeVmess.setServiceId(services.getId());
                        nodeVmess.setServerId(server.getId());
                        nodeVmess.setServiceYear(DateUtil.year(currentDate));
                        nodeVmess.setServiceMonth(DateUtil.month(currentDate) + 1);
                        nodeVmess.setServiceDate(DateUtil.beginOfDay(currentDate).toJdkDate());
                        nodeVmess.setServiceUp(0L);
                        nodeVmess.setServiceDown(0L);
                        nodeVmess.setNodeId(services.getUuid());
                        nodeVmess.setNodeType("private");
                        nodeVmess.setNodePs(server.getSubscribeNamePrefix());
                        nodeVmess.setNodeAdd(server.getSubscribeIp());
                        nodeVmess.setNodePort(String.valueOf(server.getSubscribePort()));
                        nodeVmess.setAid("0");
                        nodeVmess.setScy(null);
                        nodeVmess.setNet("tcp");
                        nodeVmess.setType("none");
                        nodeVmess.setHost(null);
                        nodeVmess.setPath(null);
                        nodeVmess.setTls("none");
                        nodeVmess.setSni(null);
                        nodeVmess.setSort(server.getSort());
                        nodeVmess.setMultiplyingPower(server.getMultiplyingPower());
                        nodeVmessDomainService.save(nodeVmess);
                    }
                }
            }
        }
    }
}
