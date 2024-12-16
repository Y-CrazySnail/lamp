package com.lamp.domain.service.web;

import com.lamp.domain.entity.Server;
import com.lamp.infrastructure.persistence.repository.web.ServerWebRepository;
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
