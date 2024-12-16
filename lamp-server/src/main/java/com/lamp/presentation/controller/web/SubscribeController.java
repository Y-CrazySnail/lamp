package com.lamp.presentation.controller.web;

import com.lamp.application.service.web.ServiceWebAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private ServiceWebAppService serviceAppService;

    @GetMapping("/test")
    public String test() {
        serviceAppService.sync();
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
