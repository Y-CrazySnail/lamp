package com.yeem.lamp.presentation.controller;

import com.yeem.lamp.application.service.MemberAppService;
import com.yeem.lamp.application.service.ServerAppService;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        serverAppService.syncRemoteServer(id);
        return "ok";
    }

    @GetMapping("/test")
    public String test() {
        serviceAppService.syncServiceRecord();
        return "ok";
    }

    @GetMapping("/syncService")
    public String syncRemoteService(@RequestParam(value = "id", required = false) Long id) {
        serverAppService.syncRemoteService(id);
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
