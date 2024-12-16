package com.lamp.controller.web;

import com.lamp.entity.LampService;
import com.lamp.service.web.LampSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-subscription")
public class LampSubscriptionController {

    @Autowired
    private LampSubscriptionService lampSubscriptionService;

    @GetMapping
    public List<LampService> getAllLampSubscriptions() {
        return lampSubscriptionService.list();
    }

    @GetMapping("/{id}")
    public LampService getLampSubscriptionById(@PathVariable Long id) {
        return lampSubscriptionService.getById(id);
    }

    @PostMapping
    public LampService createLampSubscription(@RequestBody LampService lampService) {
        return null;
    }

    @PutMapping("/{id}")
    public LampService updateLampSubscription(@PathVariable Long id, @RequestBody LampService lampService) {
        lampService.setId(id);
        return lampSubscriptionService.updateById(lampService) ? lampService : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampSubscription(@PathVariable Long id) {
        return lampSubscriptionService.removeById(id);
    }
}
