package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneOrder;
import com.yeem.one.service.IOneOrderService;
import com.yeem.one.service.IOneSkuService;
import com.yeem.one.service.IOneSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序端-订单
 */
@Slf4j
@RestController
@RequestMapping("/wechat/order")
public class OneWechatOrderController {

    @Autowired
    private IOneOrderService service;

    /**
     * 下单
     *
     * @param order 订单信息
     * @return a
     */
    @PostMapping("pre-order")
    public ResponseEntity<Object> preOrder(@RequestBody OneOrder order) {
        try {
            order = service.preOrder(order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("upload store file error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 下单
     *
     * @param order 订单信息
     * @return a
     */
    @PostMapping("order")
    public ResponseEntity<Object> placeOrder(@RequestBody OneOrder order) {
        try {
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("upload store file error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
