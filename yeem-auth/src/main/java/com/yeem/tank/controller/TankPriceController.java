package com.yeem.tank.controller;

import com.yeem.tank.entity.TankPrice;
import com.yeem.tank.service.ITankPriceService;
import com.yeem.common.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tank-price")
public class TankPriceController extends BaseController<TankPrice> {

    @Autowired
    private ITankPriceService priceService;

    @GetMapping("getByEntity")
    public ResponseEntity<Object> getById(TankPrice entity) {
        return ResponseEntity.ok(priceService.getByEntity(entity));
    }
}
