package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneCart;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneCartService;
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
@RequestMapping("/wechat/cart")
public class OneWechatCartController {

    @Autowired
    private IOneCartService service;

    /**
     * 获取所有
     *
     * @return 购物车列表信息
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<OneCart>> list(@RequestParam(value = "storeId") Long storeId) {
        try {
            Long userId = WechatAuthInterceptor.getUserId();
            return ResponseEntity.ok(service.listByUserIdAndStoreId(userId, storeId));
        } catch (Exception e) {
            log.error("list cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 添加至购物车
     *
     * @param cart 购物车
     * @return 购物车列表信息
     */
    public ResponseEntity<List<OneCart>> save(@RequestBody OneCart cart) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            Long userId = WechatAuthInterceptor.getUserId();
            cart.setTenantId(tenantId);
            cart.setUserId(userId);
            return ResponseEntity.ok(service.saveForWechat(cart));
        } catch (Exception e) {
            log.error("save cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 清空购物车
     *
     * @param cart 购物车
     * @return 购物车信息列表
     */
    public ResponseEntity<List<OneCart>> clear(@RequestBody OneCart cart) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            Long userId = WechatAuthInterceptor.getUserId();
            cart.setTenantId(tenantId);
            cart.setUserId(userId);
            return ResponseEntity.ok(service.clear(cart));
        } catch (Exception e) {
            log.error("clear cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
