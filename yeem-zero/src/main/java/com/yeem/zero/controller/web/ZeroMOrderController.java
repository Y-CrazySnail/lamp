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
}
