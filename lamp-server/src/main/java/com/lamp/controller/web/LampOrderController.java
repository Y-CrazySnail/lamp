package com.lamp.controller.web;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.lamp.entity.LampOrder;
import com.lamp.presentation.interceptor.LocalAuthInterceptor;
import com.lamp.service.web.LampOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/lamp-order")
public class LampOrderController {

    @Autowired
    private LampOrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(orderService.listByMemberId(memberId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询订单失败");
        }
    }

    /**
     * 下单
     *
     * @return 下单结果
     */
    @PostMapping("/place")
    public ResponseEntity<Object> place(@RequestBody LampOrder order) {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            order.setMemberId(memberId);
            orderService.place(order);
            return ResponseEntity.ok("下单成功");
        } catch (Exception e) {
            log.error("下单失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("下单失败");
        }
    }

    /**
     * 调起支付
     * @param order 订单信息
     * @return 支付信息
     */
    @PostMapping("/pay")
    public ResponseEntity<Object> pay(@RequestBody LampOrder order) {
        JsonNode res = orderService.pay(order);
        return ResponseEntity.ok(res);
    }

    /**
     * 完成
     *
     * @return 完成
     */
    @RequestMapping("/finish")
    public ResponseEntity<Object> finish(@RequestParam(value = "pid", required = false) String pid,
                                         @RequestParam(value = "trade_no", required = false) String trade_no,
                                         @RequestParam(value = "out_trade_no", required = false) String out_trade_no,
                                         @RequestParam(value = "type", required = false) String type,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "money", required = false) String money,
                                         @RequestParam(value = "trade_status", required = false) String trade_status) {
        try {
            log.info("pid:{}", pid);
            log.info("trade_no:{}", trade_no);
            log.info("out_trade_no:{}", out_trade_no);
            log.info("type:{}", type);
            log.info("name:{}", name);
            log.info("money:{}", money);
            log.info("trade_status:{}", trade_status);
            if ("TRADE_SUCCESS".equals(trade_status)) {
                LampOrder order = new LampOrder();
                order.setOrderNo(out_trade_no);
                order.setTradeNo(trade_no);
                orderService.finish(order);
            }
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("支付回调异常：", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("支付回调异常");
        }
    }
}
