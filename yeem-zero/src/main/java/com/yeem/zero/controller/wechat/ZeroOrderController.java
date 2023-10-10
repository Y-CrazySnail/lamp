package com.yeem.zero.controller.wechat;

import com.yeem.log.OperateLog;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序-订单信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat-zero-order")
public class ZeroOrderController {

    @Autowired
    private IZeroOrderService zeroOrderService;

    /**
     * 下单
     *
     * @param zeroOrder 订单信息
     * @return 下单状态
     */
    @OperateLog(operateModule = "订单模块", operateType = "下单", operateDesc = "下单")
    @PostMapping("order")
    public ResponseEntity<ZeroOrder> order(@RequestBody ZeroOrder zeroOrder) {
        if (StringUtils.isEmpty(zeroOrder.getCartList()) || zeroOrder.getCartList().isEmpty()) {
            log.error("cart list is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        try {
            ZeroOrder resZeroOrder = zeroOrderService.order(zeroOrder);
            return ResponseEntity.ok(resZeroOrder);
        } catch (Exception e) {
            log.error("order error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 支付
     *
     * @param zeroOrder 订单信息
     * @return 下单状态
     */
    @OperateLog(operateModule = "订单模块", operateType = "预支付", operateDesc = "预支付")
    @PostMapping("prepay")
    public ResponseEntity<ZeroOrder> prepay(@RequestBody ZeroOrder zeroOrder) {
        try {
            ZeroOrder resZeroOrder = zeroOrderService.prepay(zeroOrder);
            return ResponseEntity.ok(resZeroOrder);
        } catch (Exception e) {
            log.error("prepay error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 已支付接口
     *
     * @param zeroOrder 订单信息
     * @return response
     */
    @OperateLog(operateModule = "订单模块", operateType = "已支付", operateDesc = "已支付")
    @PostMapping("paid")
    public ResponseEntity<Object> paid(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.paid(zeroOrder);
            return ResponseEntity.ok("paid success");
        } catch (Exception e) {
            log.error("paid error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 退款接口
     *
     * @param zeroOrder 订单信息
     * @return response
     */
    @OperateLog(operateModule = "订单模块", operateType = "退款", operateDesc = "退款")
    @PostMapping("refund")
    public ResponseEntity<Object> refund(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.refund(zeroOrder);
            return ResponseEntity.ok("refund success");
        } catch (Exception e) {
            log.error("refund error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 取消退款接口
     *
     * @param zeroOrder 订单信息
     * @return response
     */
    @OperateLog(operateModule = "订单模块", operateType = "取消退款", operateDesc = "取消退款")
    @PostMapping("cancel-refund")
    public ResponseEntity<Object> cancelRefund(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.cancelRefund(zeroOrder);
            return ResponseEntity.ok("cancel refund success");
        } catch (Exception e) {
            log.error("cancel refund error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 关闭订单接口
     *
     * @param zeroOrder 订单信息
     * @return response
     */
    @OperateLog(operateModule = "订单模块", operateType = "关闭订单", operateDesc = "关闭订单")
    @PostMapping("close")
    public ResponseEntity<Object> close(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.close(zeroOrder);
            return ResponseEntity.ok("close order success");
        } catch (Exception e) {
            log.error("close order error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 确认收货接口
     *
     * @param zeroOrder 订单信息
     * @return response
     */
    @OperateLog(operateModule = "订单模块", operateType = "确认收货", operateDesc = "确认收货")
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

    /**
     * 查询订单列表
     *
     * @param status 订单状态 1：已下单|待付款 2：已付款|待发货 3：已发货|待收货 4：已收货|待评价 5：已完成 0：交易关闭 -1：退款中
     * @param name   订单名称
     * @return 订单列表
     * @apiNote 查询订单列表
     */
    @OperateLog(operateModule = "订单模块", operateType = "查询列表", operateDesc = "查询订单列表")
    @GetMapping("list")
    public ResponseEntity<List<ZeroOrder>> list(@RequestParam("status") String status, @RequestParam("name") String name) {
        try {
            return ResponseEntity.ok(zeroOrderService.list(status, name));
        } catch (Exception e) {
            log.error("list order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 查询分销订单列表
     *
     * @param nickname 昵称
     * @return 订单列表
     * @apiNote 查询分销订单列表
     */
    @OperateLog(operateModule = "订单模块", operateType = "查询分销订单列表", operateDesc = "查询分销订单列表")
    @GetMapping("distribution")
    public ResponseEntity<List<ZeroOrder>> distribution(@RequestParam("nickName") String nickname) {
        log.info("distribution order param:{}", nickname);
        try {
            return ResponseEntity.ok(zeroOrderService.distribution(nickname));
        } catch (Exception e) {
            log.error("list order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     * @apiNote 查询订单信息
     */
    @OperateLog(operateModule = "订单模块", operateType = "查询", operateDesc = "查询订单信息")
    @GetMapping("get")
    public ResponseEntity<ZeroOrder> get(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(zeroOrderService.get(id));
        } catch (Exception e) {
            log.error("get order error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 删除订单信息
     *
     * @param zeroOrder 订单信息
     * @return 删除结果
     * @apiNote 删除订单信息
     */
    @OperateLog(operateModule = "订单模块", operateType = "删除", operateDesc = "删除查询订单")
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
