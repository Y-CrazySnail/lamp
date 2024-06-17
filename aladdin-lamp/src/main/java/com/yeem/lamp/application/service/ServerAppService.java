package com.yeem.lamp.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServerDTO;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.service.ServerDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}
