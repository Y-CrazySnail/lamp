package com.yeem.zero.controller.wechat;

import com.yeem.zero.entity.ZeroCategory;
import com.yeem.zero.service.IZeroCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品类别信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat-zero-category")
public class ZeroCategoryController {

    @Autowired
    private IZeroCategoryService zeroCategoryService;

    /**
     * 查询商品类别信息列表
     *
     * @return 商品类别信息列表
     * @apiNote 查询商品类别信息列表
     */
    @GetMapping("list")
    public ResponseEntity<List<ZeroCategory>> list() {
        List<ZeroCategory> zeroCategoryList;
        try {
            zeroCategoryList = zeroCategoryService.list();
        } catch (Exception e) {
            log.error("list category error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(zeroCategoryList);
    }
}
