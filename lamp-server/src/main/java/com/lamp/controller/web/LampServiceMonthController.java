package com.lamp.controller.web;

import com.lamp.entity.LampService;
import com.lamp.service.web.LampServiceMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-service-month")
public class LampServiceMonthController {

    @Autowired
    private LampServiceMonthService lampServiceMonthService;

    @GetMapping
    public List<LampService> getAllLampServiceMonths() {
        return lampServiceMonthService.list();
    }

    @GetMapping("/{id}")
    public LampService getLampServiceMonthById(@PathVariable Long id) {
        return lampServiceMonthService.getById(id);
    }

    @PostMapping
    public LampService createLampServiceMonth(@RequestBody LampService lampService) {
        return null;
    }

    @PutMapping("/{id}")
    public LampService updateLampServiceMonth(@PathVariable Long id, @RequestBody LampService lampService) {
        lampService.setId(id);
        return lampServiceMonthService.updateById(lampService) ? lampService : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampServiceMonth(@PathVariable Long id) {
        return lampServiceMonthService.removeById(id);
    }
}
