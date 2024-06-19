package com.yeem.lamp.presentation.controller;

import com.yeem.lamp.application.service.MemberAppService;
import com.yeem.lamp.application.service.ServerAppService;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private ServerAppService serverAppService;
    @Autowired
    private ServiceAppService serviceAppService;
    @Autowired
    private MemberAppService memberAppService;

    @Autowired
    private ServiceDomainService serviceDomainService;

    @GetMapping("/syncServer/{id}")
    public String syncRemoteServer(@PathVariable("id") Long id) {
        serviceDomainService.syncRemoteServer(id);
        return "ok";
    }

    @GetMapping("/test")
    public String test() {
        serviceAppService.clash("c9e78bb8-e1a5-49b8-8ca8-8fe9b3b78d96");
        return "ok";
    }

    @GetMapping("/syncService/{id}")
    public String syncRemoteService(@PathVariable("id") Long id) {
        serviceDomainService.syncRemoteService(id);
        return "ok";
    }

    @GetMapping("/clash/{uuid}")
    public String clash(@PathVariable("uuid") String uuid) {
        return serviceAppService.clash(uuid);
    }

    @GetMapping("/shadowrocket/{uuid}")
    public String shadowrocket(@PathVariable("uuid") String uuid) {
        return serviceAppService.v2ray(uuid);
    }
}
