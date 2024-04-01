package com.yeem.one.controller.wechat;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yeem.one.entity.OneOrder;
import com.yeem.one.log.OperateLog;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
     * 预下单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @PostMapping("pre-order")
    public ResponseEntity<Object> preOrder(@RequestBody OneOrder order) {
        try {
            order = service.preOrder(order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("pre order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 下单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @PostMapping("place-order")
    @OperateLog(operateModule = "订单模块", operateType = "下单", operateDesc = "下单")
    public ResponseEntity<Object> placeOrder(@RequestBody OneOrder order) {
        try {
            order.setTenantId(WechatAuthInterceptor.getTenantId());
            order.setUserId(WechatAuthInterceptor.getUserId());
            return ResponseEntity.ok(service.placeOrder(order));
        } catch (Exception e) {
            log.error("place order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 预支付
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @PostMapping("prepay-order")
    @OperateLog(operateModule = "订单模块", operateType = "预支付", operateDesc = "预支付")
    public ResponseEntity<Object> prepayOrder(@RequestBody OneOrder order) {
        try {
            return ResponseEntity.ok(service.prepayOrder(order));
        } catch (Exception e) {
            log.error("prepay order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 退款申请
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @PostMapping("refund-apply")
    @OperateLog(operateModule = "订单模块", operateType = "申请退款", operateDesc = "申请退款")
    public ResponseEntity<Object> refundApply(@RequestBody OneOrder order) {
        try {
            return ResponseEntity.ok(service.refundApply(order));
        } catch (Exception e) {
            log.error("refund apply error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 确认收货
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @PostMapping("delivery-order")
    @OperateLog(operateModule = "订单模块", operateType = "确认收货", operateDesc = "确认收货")
    public ResponseEntity<Object> deliveryOrder(@RequestBody OneOrder order) {
        try {
            return ResponseEntity.ok(service.deliveryOrder(order));
        } catch (Exception e) {
            log.error("delivery order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 评价
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @PostMapping("review-order")
    @OperateLog(operateModule = "订单模块", operateType = "评价", operateDesc = "评价")
    public ResponseEntity<Object> reviewOrder(@RequestBody OneOrder order) {
        try {
            return ResponseEntity.ok(service.reviewOrder(order));
        } catch (Exception e) {
            log.error("review order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 关闭订单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @PostMapping("close-order")
    @OperateLog(operateModule = "订单模块", operateType = "关闭订单", operateDesc = "关闭订单")
    public ResponseEntity<Object> closeOrder(@RequestBody OneOrder order) {
        try {
            return ResponseEntity.ok(service.closeOrder(order));
        } catch (Exception e) {
            log.error("close order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
    @PostMapping("paymentCallback/{orderNo}")
    public ResponseEntity<Object> callback(@PathVariable("orderNo") String orderNo,
                                           @RequestBody ObjectNode objectNode, HttpServletRequest request) {
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");
            service.paymentCallback(timestamp, nonce, serialNo, signature, objectNode, orderNo);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("payment notify error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("notify error");
        }
    }

    /**
     * 退款回调
     *
     * @param objectNode 退款回调参数
     * @param request    request
     * @return 回调状态
     */
    @OperateLog(operateModule = "支付模块", operateType = "退款回调", operateDesc = "退款回调")
    @PostMapping("refundCallback/{orderNo}")
    public ResponseEntity<Object> refundCallback(@PathVariable("orderNo") String orderNo,
                                                 @RequestBody ObjectNode objectNode, HttpServletRequest request) {
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");
            service.refundCallback(timestamp, nonce, serialNo, signature, objectNode, orderNo);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("refund notify error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("notify error");
        }
    }
}
