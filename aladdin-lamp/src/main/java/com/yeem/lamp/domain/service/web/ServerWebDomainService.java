package com.yeem.lamp.domain.service.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.infrastructure.persistence.repository.web.ServerWebRepository;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServerWebDomainService {

    @Autowired
    private ServerWebRepository serverRepository;

    public Server getById(Long id) {
        return serverRepository.getById(id);
    }

    public List<Server> list() {
        return serverRepository.list();
    }

    /**
     * 重重所有客户端流量
     */
    public void resetClientTraffic() {
        List<Server> serverList = serverRepository.list();
        for (Server server : serverList) {
            try {
                XUIClient xuiClient = XUIClient.init(server);
                XInbound xInbound = xuiClient.getInbound();
                xuiClient.resetClientTraffic(xInbound.getId());
            } catch (Exception e) {
                log.error("服务器：{}-{}异常：", server.getApiIp(), server.getRegion(), e);
            }
        }
    }

}
