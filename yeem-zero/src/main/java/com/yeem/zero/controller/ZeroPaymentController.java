package com.yeem.zero.controller;

import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.common.conreoller.BaseController;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
