package com.yeem.zero.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.common.conreoller.BaseController;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/zero-payment")
public class ZeroPaymentController extends BaseController<ZeroOrder> {

    @Autowired
    private IZeroPaymentService zeroPaymentService;

    @PostMapping("wechat-prepay")
    public ResponseEntity<Object> wechatPrepay(@RequestBody ZeroOrder zeroOrder) {
        try {
            PrepayWithRequestPaymentResponse response = zeroPaymentService.wechatPrepay(zeroOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("wechat prepay error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("wechat prepay error");
        }
    }

    @PostMapping("callback")
    public ResponseEntity<Object> callback(@RequestBody ObjectNode objectNode) {
        try {
            zeroPaymentService.callback(objectNode);
            log.info("支付回调：{}", objectNode);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("notify error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("notify error");
        }
    }
}
