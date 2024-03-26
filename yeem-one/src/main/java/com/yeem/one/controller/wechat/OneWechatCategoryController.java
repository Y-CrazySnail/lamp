package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneCategory;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneCategoryService;
import com.yeem.one.service.IOneStoreService;
import com.yeem.one.service.IOneTenantService;
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
    @Autowired
    private IOneTenantService oneTenantService;
    @Autowired
    private IOneStoreService oneStoreService;

    /**
     * 获取所有
     *
     * @return 分类信息
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<OneCategory>> list(@RequestBody OneCategory category) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            category.setTenantId(tenantId);
            return ResponseEntity.ok(service.listForWechat(category));
        } catch (Exception e) {
            log.error("get category by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
