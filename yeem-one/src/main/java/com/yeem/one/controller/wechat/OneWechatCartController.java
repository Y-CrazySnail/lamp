package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneCart;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return 购物车信息
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<OneCart>> list() {
        try {
            Long userId = WechatAuthInterceptor.getUserId();
            return ResponseEntity.ok(service.listByUserId(userId));
        } catch (Exception e) {
            log.error("list cart error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
