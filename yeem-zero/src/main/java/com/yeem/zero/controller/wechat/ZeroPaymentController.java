package com.yeem.zero.controller.wechat;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.log.OperateLog;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信小程序-支付功能
 */
@Slf4j
@RestController
@RequestMapping("/wechat/zero-payment")
public class ZeroPaymentController {

    private final static String HEADER_NAME_TIMESTAMP = "Wechatpay-Timestamp";
    private final static String HEADER_NAME_NONCE = "Wechatpay-Nonce";
    private final static String HEADER_NAME_SERIAL = "Wechatpay-Serial";
    private final static String HEADER_NAME_SIGNATURE = "Wechatpay-Signature";

    @Autowired
    private IZeroPaymentService zeroPaymentService;

    /**
     * 预支付
     *
     * @param zeroOrder 订单信息
     * @return 预支付信息
     * @apiNote 预支付
     */
    @PostMapping("wechat-prepay")
    public ResponseEntity<PrepayWithRequestPaymentResponse> wechatPrepay(@RequestBody ZeroOrder zeroOrder) {
        try {
            PrepayWithRequestPaymentResponse response = zeroPaymentService.wechatPrepay(zeroOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("wechat prepay error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 支付回调
     *
     * @param objectNode 支付回调参数
     * @param request    request
     * @return 回调状态
     */
    @OperateLog(operateModule = "支付模块", operateType = "支付回调", operateDesc = "支付回调")
    @PostMapping("callback")
    public ResponseEntity<Object> callback(@RequestBody ObjectNode objectNode, HttpServletRequest request) {
        try {
            String timestamp = request.getHeader(HEADER_NAME_TIMESTAMP);
            String nonce = request.getHeader(HEADER_NAME_NONCE);
            String serialNo = request.getHeader(HEADER_NAME_SERIAL);
            String signature = request.getHeader(HEADER_NAME_SIGNATURE);
            zeroPaymentService.callback(timestamp, nonce, serialNo, signature, objectNode);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("notify error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("notify error");
        }
    }
}
