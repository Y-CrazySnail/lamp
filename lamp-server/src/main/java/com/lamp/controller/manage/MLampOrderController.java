package com.lamp.controller.manage;

import com.lamp.entity.LampOrder;
import com.lamp.service.manage.MLampOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/lamp-order")
public class MLampOrderController {

    @Autowired
    private MLampOrderService lampOrderService;

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
