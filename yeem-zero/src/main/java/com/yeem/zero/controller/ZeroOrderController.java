package com.yeem.zero.controller;

import com.yeem.common.aspect.log.OperateLog;
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

    /**
     * 支付
     *
     * @param zeroOrder 入参对象
     * @return 下单状态
     */
    @PostMapping("prepay")
    public ResponseEntity<Object> prepay(@RequestBody ZeroOrder zeroOrder) {
        try {
            ZeroOrder resZeroOrder = zeroOrderService.prepay(zeroOrder);
            return ResponseEntity.ok(resZeroOrder);
        } catch (Exception e) {
            log.error("prepay error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("prepay error");
        }
    }

    /**
     * 已支付接口
     *
     * @param zeroOrder 订单信息
     * @return response
     */
    @PostMapping("paid")
    public ResponseEntity<Object> paid(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.paid(zeroOrder);
            return ResponseEntity.ok("paid success");
        } catch (Exception e) {
            log.error("paid error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("paid error");
        }
    }

    /**
     * 确认收货
     *
     * @param zeroOrder 确认收货
     * @return response
     */
    @PostMapping("confirm")
    public ResponseEntity<Object> confirm(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.confirm(zeroOrder);
            return ResponseEntity.ok("confirm success");
        } catch (Exception e) {
            log.error("confirm error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("confirm error");
        }
    }

    @OperateLog(operateModule = "订单模块", operateType = "查询订单列表", operateDesc = "描述:查询订单列表")
    @GetMapping("list")
    public ResponseEntity<Object> list(@RequestParam("status") String status,
                                       @RequestParam("name") String name) {
        try {
            return ResponseEntity.ok(zeroOrderService.list(status, name));
        } catch (Exception e) {
            log.error("list order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("list order error");
        }
    }

    @GetMapping("get")
    public ResponseEntity<Object> get(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(zeroOrderService.get(id));
        } catch (Exception e) {
            log.error("get order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("get order error");
        }
    }

    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.remove(zeroOrder.getId());
            return ResponseEntity.ok("remove order success");
        } catch (Exception e) {
            log.error("remove order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("remove order error");
        }
    }
}
