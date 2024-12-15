package com.yeem.lamp.controller.web;

import com.yeem.lamp.entity.LampService;
import com.yeem.lamp.service.LampServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-service")
public class LampServiceController {

    @Autowired
    private LampServiceService lampServiceService;

    @GetMapping
    public List<LampService> getAllLampServices() {
        return lampServiceService.list();
    }

    @GetMapping("/{id}")
    public LampService getLampServiceById(@PathVariable Long id) {
        return lampServiceService.getById(id);
    }

    @PostMapping
    public LampService createLampService(@RequestBody LampService lampService) {
        return null;
    }

    @PutMapping("/{id}")
    public LampService updateLampService(@PathVariable Long id, @RequestBody LampService lampService) {
        lampService.setId(id);
        return lampServiceService.updateById(lampService) ? lampService : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampService(@PathVariable Long id) {
        return lampServiceService.removeById(id);
    }
}
