package com.yeem.zero.controller;

import com.yeem.common.conreoller.BaseController;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/zero-order")
public class ZeroOrderController extends BaseController<ZeroOrder> {

    @Autowired
    private IZeroOrderService zeroOrderService;

    /**
     * 下单
     * @param zeroOrder 入参对象
     * @return 下单状态
     */
    @PostMapping("order")
    public ResponseEntity<Object> order(@RequestBody ZeroOrder zeroOrder) {
        try {
            ZeroOrder resZeroOrder = zeroOrderService.order(zeroOrder);
            return ResponseEntity.ok(resZeroOrder);
        } catch (Exception e) {
            log.error("order error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("order error");
        }
    }
}
