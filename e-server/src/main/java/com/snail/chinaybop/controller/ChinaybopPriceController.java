package com.snail.chinaybop.controller;

import com.snail.conreoller.BaseController;
import com.snail.chinaybop.entity.ChinaybopPrice;
import com.snail.chinaybop.service.IChinaybopPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chinaybop-price")
public class ChinaybopPriceController extends BaseController<ChinaybopPrice> {

    @Autowired
    private IChinaybopPriceService priceService;

    @GetMapping("getByEntity")
    public ResponseEntity<Object> getById(ChinaybopPrice entity) {
        return ResponseEntity.ok(priceService.getByEntity(entity));
    }
}
