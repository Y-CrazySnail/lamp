package com.yeem.lamp.application.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServerDTO;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.service.manage.ServerManageDomainService;
import com.yeem.lamp.domain.service.manage.ServiceManageDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServerManageAppService {

    @Autowired
    private ServerManageDomainService serverDomainService;
    @Autowired
    private ServiceManageDomainService serviceDomainService;

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
