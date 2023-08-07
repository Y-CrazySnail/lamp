package com.yeem.zero.controller;

import com.yeem.common.conreoller.BaseController;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/zero-order")
public class ZeroOrderController extends BaseController<ZeroOrder> {

    @Autowired
    private IZeroOrderService zeroOrderService;

    /**
     * 下单
     *
     * @param zeroOrder 入参对象
     * @return 下单状态
     */
    @PostMapping("order")
    public ResponseEntity<Object> order(@RequestBody ZeroOrder zeroOrder) {
        if (StringUtils.isEmpty(zeroOrder.getCartList()) || zeroOrder.getCartList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cart list is empty");
        }
        try {
            ZeroOrder resZeroOrder = zeroOrderService.order(zeroOrder);
            return ResponseEntity.ok(resZeroOrder);
        } catch (Exception e) {
            log.error("order error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("order error");
        }
    }

    @GetMapping("list")
    public ResponseEntity<Object> list(@RequestParam String status) {
        try {
            return ResponseEntity.ok(zeroOrderService.list(status));
        } catch (Exception e) {
            log.error("list order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("list order error");
        }
    }
}
