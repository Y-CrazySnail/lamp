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
}
