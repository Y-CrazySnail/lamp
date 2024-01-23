package com.yeem.lamp.controller.web;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.entity.AladdinOrder;
import com.yeem.lamp.security.LocalAuthInterceptor;
import com.yeem.lamp.service.IAladdinOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/order")
public class WebAladdinOrderController {

    @Autowired
    private IAladdinOrderService aladdinOrderService;

    /**
     * 列表查询
     *
     * @return 订单信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(aladdinOrderService.listByMemberId(memberId));
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
    public ResponseEntity<Object> place(@RequestBody AladdinOrder aladdinOrder) {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            aladdinOrder.setMemberId(memberId);
            aladdinOrderService.place(aladdinOrder);
            return ResponseEntity.ok("下单成功");
        } catch (Exception e) {
            log.error("下单", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("下单");
        }
    }

    @GetMapping("/pay")
    public ResponseEntity<Object> pay(){
        AladdinOrder aladdinOrder = new AladdinOrder();
        aladdinOrderService.pay(aladdinOrder);
        return ResponseEntity.ok("ok");
    }

    /**
     * 完成
     *
     * @return 完成
     */
    @PostMapping("/finish")
    public ResponseEntity<Object> finish(@RequestBody AladdinOrder aladdinOrder) {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            aladdinOrder.setMemberId(memberId);
            aladdinOrderService.finish(aladdinOrder);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("支付", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("支付");
        }
    }
}
