package com.yeem.one.controller.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.fss.entity.SysFS;
import com.yeem.fss.service.ISysFSService;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneOrder;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneOrderService;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 管理端-订单
 */
@Slf4j
@RestController
@RequestMapping("/manage/order")
public class OneOrderController {

    @Autowired
    private IOneOrderService service;
    @Autowired
    private IOneTenantService tenantService;
    @Resource(name = "COSSysFSServiceImpl")
    private ISysFSService sysFSService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 订单分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneOrder>> getPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                   @RequestParam(value = "orderNo", required = false) String orderNo,
                                                   @RequestParam(value = "orderName", required = false) String orderName,
                                                   @RequestParam(value = "orderStatus", required = false) String orderStatus,
                                                   @RequestParam(value = "refundFlag", required = false) Boolean refundFlag,
                                                   @RequestParam(value = "userId", required = false) Long userId) {
        IPage<OneOrder> page = new Page<>(current, size);
        LambdaQueryWrapper<OneOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneOrder::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.in(OneOrder::getTenantId, tenantService.authorizedTenantIdSet());
        if (!StrUtil.isEmpty(orderNo)) {
            queryWrapper.like(OneOrder::getOrderNo, orderNo);
        }
        if (!StrUtil.isEmpty(orderName)) {
            queryWrapper.like(OneOrder::getOrderName, orderName);
        }
        if (!StrUtil.isEmpty(orderStatus)) {
            queryWrapper.eq(OneOrder::getOrderStatus, orderStatus);
        }
        if (null != refundFlag) {
            queryWrapper.eq(OneOrder::getRefundFlag, refundFlag);
        }
        if (null != userId) {
            queryWrapper.eq(OneOrder::getUserId, userId);
        }
        try {
            return ResponseEntity.ok(service.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("get order page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取
     *
     * @param id 订单ID
     * @return 订单信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneOrder> getById(@RequestParam(value = "id") Long id) {
        try {
            return ResponseEntity.ok(service.getByIdWithOther(id));
        } catch (Exception e) {
            log.error("get order by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 更新订单信息
     *
     * @return 订单信息
     * @apiNote 更新订单信息
     */
    @OperateLog(operateModule = "订单模块", operateType = "更新订单信息", operateDesc = "更新订单信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneOrder order) {
        try {
            tenantService.authenticate(order.getTenantId());
            service.updateById(order);
        } catch (Exception e) {
            log.error("update order info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(order);
    }

    /**
     * 上传订单文件
     *
     * @param file 文件
     * @return URL
     */
    @PostMapping("upload")
    public ResponseEntity<Object> upload(@RequestPart("file") MultipartFile file) {
        try {
            SysFS sysFS = new SysFS("order");
            String url = sysFSService.upload(Constant.APPLICATION, sysFS, file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            log.error("upload order file error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
