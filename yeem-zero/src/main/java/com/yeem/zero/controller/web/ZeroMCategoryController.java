package com.yeem.zero.controller.web;

import com.yeem.log.OperateLog;
import com.yeem.zero.entity.ZeroCategory;
import com.yeem.zero.service.IZeroCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理端-商品类别信息
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-category")
public class ZeroMCategoryController {

    @Autowired
    private IZeroCategoryService zeroCategoryService;

    /**
     * 查询商品类别信息字典
     *
     * @return 商品类别信息字典
     * @apiNote 查询商品类别信息字典
     */
    @OperateLog(operateModule = "商品类别模块", operateType = "查询字典", operateDesc = "查询商品类别字典")
    @GetMapping("dict")
    public ResponseEntity<List<ZeroCategory>> dict() {
        List<ZeroCategory> zeroCategoryList;
        try {
            zeroCategoryList = zeroCategoryService.dict();
        } catch (Exception e) {
            log.error("list category dict error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(zeroCategoryList);
    }
}
