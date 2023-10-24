package com.yeem.zero.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.service.IZeroOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端-订单信息
 */
@Slf4j
@RestController
@RequestMapping("/manager-zero-order")
public class ZeroMOrderController {

    @Autowired
    private IZeroOrderService zeroOrderService;

    /**
     * 订单分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 订单分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<ZeroOrder>> getPage(@RequestParam("current") Integer current,
                                                    @RequestParam("size") Integer size,
                                                    @RequestParam(value = "userId", required = false) Long userId,
                                                    @RequestParam(value = "status", required = false) String status) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<ZeroOrder> page = new Page<>(current, size);
        QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            zeroOrderQueryWrapper.eq("user_id", userId);
        }
        if (!StringUtils.isEmpty(status)) {
            zeroOrderQueryWrapper.eq("status", status);
        }
        try {
            return ResponseEntity.ok(zeroOrderService.page(page, zeroOrderQueryWrapper));
        } catch (Exception e) {
            log.error("get order page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID查询查询
     *
     * @return 订单详细信息
     */
    @GetMapping("getById")
    public ResponseEntity<ZeroOrder> getById(@RequestParam(value = "id") Long id) {
        try {
            return ResponseEntity.ok(zeroOrderService.getById(id));
        } catch (Exception e) {
            log.error("get order by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 更新订单信息
     *
     * @param zeroOrder 订单信息
     * @return 修改状态
     */
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroOrder zeroOrder) {
        zeroOrderService.updateById(zeroOrder);
        return ResponseEntity.ok("更新成功");
    }

    /**
     * 发货
     *
     * @param zeroOrder 订单信息
     * @return 修改状态
     */
    @PutMapping("shipment")
    public ResponseEntity<Object> shipment(@RequestBody ZeroOrder zeroOrder) {
        log.info("order id:{}, shipment", zeroOrder.getId());
        try {
            zeroOrderService.shipment(zeroOrder);
            return ResponseEntity.ok("发货成功");
        } catch (Exception e) {
            log.error("order id:{} shitment error", zeroOrder.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 退款
     *
     * @param zeroOrder 订单信息
     * @apiNote 退款
     */
    @PostMapping("refund")
    public ResponseEntity<Object> wechatRefund(@RequestBody ZeroOrder zeroOrder) {
        try {
            zeroOrderService.wechatRefund(zeroOrder);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            log.error("wechat refund error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
