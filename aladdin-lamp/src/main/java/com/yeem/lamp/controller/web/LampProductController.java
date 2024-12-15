package com.yeem.lamp.controller.web;

import com.yeem.lamp.entity.LampProduct;
import com.yeem.lamp.service.LampProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/lamp-product")
public class LampProductController {

    @Autowired
    private LampProductService lampProductService;

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
