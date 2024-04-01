package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneCategory;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序端-分类
 */
@Slf4j
@RestController
@RequestMapping("/wechat/category")
public class OneWechatCategoryController {

    @Autowired
    private IOneCategoryService service;

    /**
     * 获取所有
     *
     * @return 分类信息
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<OneCategory>> list(@RequestParam(value = "storeId", required = false) Long storeId) {
        try {
            OneCategory category = new OneCategory();
            Long tenantId = WechatAuthInterceptor.getTenantId();
            category.setTenantId(tenantId);
            category.setStoreId(storeId);
            return ResponseEntity.ok(service.listForWechat(category));
        } catch (Exception e) {
            log.error("get category by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
