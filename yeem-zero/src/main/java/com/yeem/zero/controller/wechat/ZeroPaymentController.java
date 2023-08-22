package com.yeem.zero.controller.wechat;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.common.conreoller.BaseController;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/wechat-zero-payment")
public class ZeroPaymentController {

    private final static String HEADER_NAME_TIMESTAMP = "Wechatpay-Timestamp";
    private final static String HEADER_NAME_NONCE = "Wechatpay-Nonce";
    private final static String HEADER_NAME_SERIAL = "Wechatpay-Serial";
    private final static String HEADER_NAME_SIGNATURE = "Wechatpay-Signature";

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

    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;

        try {
            StringBuilder result = new StringBuilder();

            String line;
            for (br = request.getReader(); (line = br.readLine()) != null; result.append(line)) {
                if (result.length() > 0) {
                    result.append("\n");
                }
            }

            line = result.toString();
            return line;
        } catch (IOException var12) {
            throw new RuntimeException(var12);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var11) {
                    var11.printStackTrace();
                }
            }

        }
    }
}
