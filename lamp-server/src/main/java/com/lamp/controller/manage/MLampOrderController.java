package com.lamp.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampOrder;
import com.lamp.service.manage.MLampOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage/lamp-order")
public class MLampOrderController {

    @Autowired
    private MLampOrderService orderService;

    @GetMapping("/page")
    public ResponseEntity<Object> page(LampOrder order) {
        try {
            IPage<LampOrder> page = new Page<>();
            LambdaQueryWrapper<LampOrder> queryWrapper = new LambdaQueryWrapper<>(LampOrder.class);
            BaseEntity.setDeleteFlagCondition(queryWrapper);
            return ResponseEntity.ok(orderService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("获取订单列表失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取订单列表失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam(value = "id") Long id) {
        try {
            return ResponseEntity.ok(orderService.getById(id));
        } catch (Exception e) {
            log.error("获取订单详情失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取订单详情失败");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> addMember(@RequestBody LampOrder order) {
        try {
            orderService.save(order);
            return ResponseEntity.ok("订单添加成功");
        } catch (Exception e) {
            log.error("添加订单失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加订单失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateMember(@RequestBody LampOrder order) {
        try {
            orderService.updateById(order);
            return ResponseEntity.ok("订单信息更新成功");
        } catch (Exception e) {
            log.error("更新订单信息失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新订单信息失败");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> remove(@RequestBody LampOrder order) {
        try {
            orderService.removeById(order.getId());
            return ResponseEntity.ok("订单删除成功");
        } catch (Exception e) {
            log.error("删除订单失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除订单失败");
        }
    }
}
