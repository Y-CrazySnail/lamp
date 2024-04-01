package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneAddress;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序端-购物车
 */
@Slf4j
@RestController
@RequestMapping("/wechat/address")
public class OneWechatAddressController {

    @Autowired
    private IOneAddressService service;

    /**
     * 获取所有
     *
     * @return 购物车信息
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<OneAddress>> list() {
        try {
            Long userId = WechatAuthInterceptor.getUserId();
            return ResponseEntity.ok(service.listByUserId(userId));
        } catch (Exception e) {
            log.error("list cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "save")
    public ResponseEntity<List<OneAddress>> save(@RequestBody OneAddress address) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            Long userId = WechatAuthInterceptor.getUserId();
            address.setTenantId(tenantId);
            address.setUserId(userId);
            return ResponseEntity.ok(service.saveForWechat(address));
        } catch (Exception e) {
            log.error("save cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "update")
    public ResponseEntity<List<OneAddress>> update(@RequestBody OneAddress address) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            Long userId = WechatAuthInterceptor.getUserId();
            address.setTenantId(tenantId);
            address.setUserId(userId);
            return ResponseEntity.ok(service.updateForWechat(address));
        } catch (Exception e) {
            log.error("update cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "remove")
    public ResponseEntity<List<OneAddress>> remove(@RequestBody OneAddress address) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            Long userId = WechatAuthInterceptor.getUserId();
            address.setTenantId(tenantId);
            address.setUserId(userId);
            return ResponseEntity.ok(service.removeForWechat(address));
        } catch (Exception e) {
            log.error("remove cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
