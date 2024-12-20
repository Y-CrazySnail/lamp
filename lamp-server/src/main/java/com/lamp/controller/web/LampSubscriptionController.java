package com.lamp.controller.web;

import com.lamp.entity.LampService;
import com.lamp.service.web.LampSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class LampSubscriptionController {

    @Autowired
    private LampSubscriptionService subscriptionService;

    @GetMapping("/clash/{uuid}")
    public String clash(@PathVariable("uuid") String uuid) {
        return subscriptionService.clash(uuid);
    }

    @GetMapping("/shadowrocket/{uuid}")
    public String shadowrocket(@PathVariable("uuid") String uuid) {
        return subscriptionService.v2ray(uuid);
    }

}
