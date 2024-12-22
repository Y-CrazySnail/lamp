package com.lamp.controller.web;

import com.lamp.service.web.LampProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/lamp-product")
public class LampProductController {

    @Autowired
    private LampProductService productService;

    @GetMapping(value = "list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(productService.list());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询产品失败");
        }
    }

}
