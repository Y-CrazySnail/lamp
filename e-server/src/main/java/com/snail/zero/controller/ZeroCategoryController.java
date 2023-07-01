package com.snail.zero.controller;

import com.snail.conreoller.BaseController;
import com.snail.zero.entity.ZeroCategory;
import com.snail.zero.service.IZeroCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/zero-category")
public class ZeroCategoryController extends BaseController<ZeroCategory> {

    @Autowired
    private IZeroCategoryService zeroCategoryService;

    @GetMapping("list")
    public ResponseEntity<Object> list() {
        List<ZeroCategory> zeroCategoryList;
        try {
            zeroCategoryList = zeroCategoryService.list();
        } catch (Exception e) {
            log.error("list category error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("list category error");
        }
        return ResponseEntity.ok(zeroCategoryList);
    }
}
