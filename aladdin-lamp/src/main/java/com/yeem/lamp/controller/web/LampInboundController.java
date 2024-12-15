package com.yeem.lamp.controller.web;

import com.yeem.lamp.entity.LampInbound;
import com.yeem.lamp.service.LampInboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-inbound")
public class LampInboundController {

    @Autowired
    private LampInboundService lampInboundService;

    @GetMapping
    public List<LampInbound> getAllLampInbound() {
        return lampInboundService.list();
    }

    @GetMapping("/{id}")
    public LampInbound getLampInboundById(@PathVariable Long id) {
        return lampInboundService.getById(id);
    }

    @PostMapping
    public LampInbound createLampInbound(@RequestBody LampInbound lampInbound) {
        return null;
    }

    @PutMapping("/{id}")
    public LampInbound updateLampInbound(@PathVariable Long id, @RequestBody LampInbound lampInbound) {
        lampInbound.setId(id);
        return lampInboundService.updateById(lampInbound) ? lampInbound : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampInbound(@PathVariable Long id) {
        return lampInboundService.removeById(id);
    }
}
