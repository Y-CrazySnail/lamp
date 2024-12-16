package com.lamp.controller.manage;

import com.lamp.entity.LampProduct;
import com.lamp.service.manage.MLampProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage/lamp-product")
public class MLampProductController {

    @Autowired
    private MLampProductService lampProductService;

    @GetMapping
    public List<LampProduct> getAllLampProducts() {
        return lampProductService.list();
    }

    @GetMapping("/{id}")
    public LampProduct getLampProductById(@PathVariable Long id) {
        return lampProductService.getById(id);
    }

    @PostMapping
    public LampProduct createLampProduct(@RequestBody LampProduct lampProduct) {
        return null;
    }

    @PutMapping("/{id}")
    public LampProduct updateLampProduct(@PathVariable Long id, @RequestBody LampProduct lampProduct) {
        lampProduct.setId(id);
        return lampProductService.updateById(lampProduct) ? lampProduct : null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteLampProduct(@PathVariable Long id) {
        return lampProductService.removeById(id);
    }
}
