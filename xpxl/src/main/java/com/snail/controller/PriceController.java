package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Price;
import com.snail.service.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceController extends BaseController<Price> {

    @Autowired
    private IPriceService priceService;

    @GetMapping("getByEntity")
    public ResponseEntity<Object> getById(Price entity) {
        return ResponseEntity.ok(priceService.getByEntity(entity));
    }
}
