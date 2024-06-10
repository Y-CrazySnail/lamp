package com.yeem.lamp.presentation.controller.web;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.yeem.lamp.application.dto.OrderDTO;
import com.yeem.lamp.application.service.OrderAppService;
import com.yeem.lamp.presentation.interceptor.LocalAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/order")
public class OrderWebController {

    @Autowired
    private OrderAppService orderAppService;

    /**
     * 列表查询
     *
     * @return 订单信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(orderAppService.listByMemberId(memberId));
        } catch (Exception e) {
            log.error("list查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("list查询失败");
        }
    }

    /**
     * 下单
     *
     * @return 下单结果
     */
    @PostMapping("/place")
    public ResponseEntity<Object> place(@RequestBody OrderDTO orderDTO) {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            orderDTO.setMemberId(memberId);
            orderAppService.place(orderDTO);
            return ResponseEntity.ok("下单成功");
        } catch (Exception e) {
            log.error("下单失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("下单失败");
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<Object> pay(@RequestBody OrderDTO orderDTO) {
        JsonNode res = orderAppService.pay(orderDTO);
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
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setOrderNo(out_trade_no);
                orderDTO.setTradeNo(trade_no);
                orderAppService.finish(orderDTO);
            }
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("支付回调异常：", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("支付回调异常");
        }
    }
}
