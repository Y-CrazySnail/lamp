package com.yeem.xpxl.controller;

import com.yeem.xpxl.entity.XpxlPrice;
import com.yeem.xpxl.service.IXpxlPriceService;
import com.yeem.common.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xpxl-price")
public class XpxlPriceController extends BaseController<XpxlPrice> {

    @Autowired
    private IXpxlPriceService priceService;

    @GetMapping("getByEntity")
    public ResponseEntity<Object> getById(XpxlPrice entity) {
        return ResponseEntity.ok(priceService.getByEntity(entity));
    }
}
