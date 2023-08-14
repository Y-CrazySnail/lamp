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

    @PostMapping("wechatPrepay")
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
//            // 构造 RequestParam
//            com.wechat.pay.java.core.notification.RequestParam requestParam =
//                    new com.wechat.pay.java.core.notification.RequestParam.Builder()
//                            .serialNumber(wechatPaySerial)
//                            .nonce(wechatpayNonce)
//                            .signature(wechatSignature)
//                            .timestamp(wechatTimestamp)
//                            .body(requestBody)
//                            .build();
//
//// 如果已经初始化了 RSAAutoCertificateConfig，可直接使用
//// 没有的话，则构造一个
//            NotificationConfig config = new RSAAutoCertificateConfig.Builder()
//                    .merchantId(merchantId)
//                    .privateKeyFromPath(privateKeyPath)
//                    .merchantSerialNumber(merchantSerialNumber)
//                    .apiV3Key(apiV3Key)
//                    .build();
//
//// 初始化 NotificationParser
//            NotificationParser parser = new NotificationParser(config);
//
//            try {
//                // 以支付通知回调为例，验签、解密并转换成 Transaction
//                Transaction transaction = (Transaction) parser.parse(requestParam, Transaction.class);
//            } catch (ValidationException e) {
//                // 签名验证失败，返回 401 UNAUTHORIZED 状态码
//                logger.error("sign verification failed", e);
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
//            }
//
//// 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
//            if (/* process error */) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//// 处理成功，返回 200 OK 状态码
//            return ResponseEntity.status(HttpStatus.OK);
            log.info("支付回调：{}", objectNode);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("notify error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("notify error");
        }
    }
}
