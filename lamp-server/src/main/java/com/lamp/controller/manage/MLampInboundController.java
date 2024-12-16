package com.lamp.controller.manage;

import com.lamp.entity.LampInbound;
import com.lamp.service.manage.MLampInboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/lamp-inbound")
public class MLampInboundController {

    @Autowired
    private MLampInboundService inboundService;

    @GetMapping
    public List<LampInbound> getAllLampInbound() {
        return inboundService.list();
    }

    @GetMapping("/{id}")
    public LampInbound getLampInboundById(@PathVariable Long id) {
        return inboundService.getById(id);
    }

    @PostMapping
    public LampInbound createLampInbound(@RequestBody LampInbound lampInbound) {
        return null;
    }

    @PutMapping("/{id}")
    public LampInbound updateLampInbound(@PathVariable Long id, @RequestBody LampInbound lampInbound) {
        lampInbound.setId(id);
        return inboundService.updateById(lampInbound) ? lampInbound : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampInbound(@PathVariable Long id) {
        return inboundService.removeById(id);
    }
}
