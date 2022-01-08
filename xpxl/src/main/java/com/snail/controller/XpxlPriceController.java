package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.XpxlPrice;
import com.snail.service.IXpxlPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class XpxlPriceController extends BaseController<XpxlPrice> {

    @Autowired
    private IXpxlPriceService priceService;

    @GetMapping("getByEntity")
    public ResponseEntity<Object> getById(XpxlPrice entity) {
        return ResponseEntity.ok(priceService.getByEntity(entity));
    }
}
