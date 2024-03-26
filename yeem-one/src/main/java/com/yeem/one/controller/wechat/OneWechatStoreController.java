package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneStore;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序端-店铺
 */
@Slf4j
@RestController
@RequestMapping("/wechat/store")
public class OneWechatStoreController {

    @Autowired
    private IOneStoreService service;

    /**
     * 获取默认店铺信息（适用于单店铺的租户）
     *
     * @return 店铺信息
     */
    @GetMapping(value = "getDefault")
    public ResponseEntity<OneStore> getDefault() {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            return ResponseEntity.ok(service.getDefault(tenantId));
        } catch (Exception e) {
            log.error("get default store error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 获取店铺信息列表
     *
     * @return 店铺信息列表
     */
    @GetMapping(value = "listForDistance")
    public ResponseEntity<OneStore> listForDistance(@RequestParam(value = "longitude", required = false) String longitude,
                                                    @RequestParam(value = "latitude", required = false) String latitude) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            return ResponseEntity.ok(service.getDefault(tenantId));
        } catch (Exception e) {
            log.error("get default store error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
