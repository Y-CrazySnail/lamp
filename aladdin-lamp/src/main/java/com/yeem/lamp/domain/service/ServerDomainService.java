package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.repository.ServerRepository;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XInbound;
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

    /**
     * 重重所有客户端流量
     */
    public void resetClientTraffic() {
        List<Server> serverList = serverRepository.list();
        for (Server server : serverList) {
            XUIClient xuiClient = XUIClient.init(server);
            XInbound xInbound = xuiClient.getInbound();
            xuiClient.resetClientTraffic(xInbound.getId());
        }
    }

}
