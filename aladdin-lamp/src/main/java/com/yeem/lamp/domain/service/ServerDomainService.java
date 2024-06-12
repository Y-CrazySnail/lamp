package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.objvalue.Server;
import com.yeem.lamp.domain.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerDomainService {

    @Autowired
    private ServerRepository serverRepository;

    public Server getById(Long id) {
        return serverRepository.getById(id);
    }

    public List<Server> list() {
        return serverRepository.list();
    }

    public IPage<Server> pages(int current, int size) {
        return serverRepository.pages(current, size);
    }

    public void updateById(Server server) {
        serverRepository.updateById(server);
    }

    public void save(Server server) {
        serverRepository.save(server);
    }

    public void removeById(Long id) {
        serverRepository.removeById(id);
    }
}
