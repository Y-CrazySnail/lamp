package com.yeem.lamp.controller.web;

import com.yeem.lamp.entity.LampOrder;
import com.yeem.lamp.service.LampOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-order")
public class LampOrderController {

    @Autowired
    private LampOrderService lampOrderService;

    @GetMapping
    public List<LampOrder> getAllLampOrders() {
        return lampOrderService.list();
    }

    @GetMapping("/{id}")
    public LampOrder getLampOrderById(@PathVariable Long id) {
        return lampOrderService.getById(id);
    }

    @PostMapping
    public LampOrder createLampOrder(@RequestBody LampOrder lampOrder) {
        return null;
    }

    @PutMapping("/{id}")
    public LampOrder updateLampOrder(@PathVariable Long id, @RequestBody LampOrder lampOrder) {
        lampOrder.setId(id);
        return lampOrderService.updateById(lampOrder) ? lampOrder : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampOrder(@PathVariable Long id) {
        return lampOrderService.removeById(id);
    }
}
