package com.yeem.one.controller.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneSku;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneSkuService;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-sku
 */
@Slf4j
@RestController
@RequestMapping("/manage/sku")
public class OneSkuController {

    @Autowired
    private IOneSkuService service;
    @Autowired
    private IOneTenantService oneTenantService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return sku分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneSku>> getPage(@RequestParam(value = "current") Integer current,
                                                 @RequestParam("size") Integer size,
                                                 @RequestParam(value = "spuId", required = false) Long spuId,
                                                 @RequestParam(value = "skuName", required = false) String skuName) {
        if (null == current) {
            current = 1;
        }
        if (null == size) {
            size = 10;
        }
        IPage<OneSku> page = new Page<>(current, size);
        LambdaQueryWrapper<OneSku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneSku::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.in(OneSku::getTenantId, oneTenantService.authorizedTenantIdSet());
        if (null != spuId) {
            queryWrapper.eq(OneSku::getSpuId, spuId);
        }
        if (StrUtil.isNotEmpty(skuName)) {
            queryWrapper.like(OneSku::getSkuName, skuName);
        }
        try {
            return ResponseEntity.ok(service.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("get sku page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取
     *
     * @param id sku ID
     * @return sku信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneSku> getById(@RequestParam(value = "id") Long id) {
        try {
            OneSku sku = service.getById(id);
            oneTenantService.authenticate(sku.getTenantId());
            return ResponseEntity.ok(sku);
        } catch (Exception e) {
            log.error("get sku by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 获取所有
     *
     * @return 店铺信息
     */
    @GetMapping(value = "getAll")
    public ResponseEntity<List<OneSku>> getAll(@RequestParam(value = "spuId", required = false) Long spuId) {
        try {
            LambdaQueryWrapper<OneSku> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OneSku::getDeleteFlag, Constant.BOOLEAN_FALSE);
            queryWrapper.in(OneSku::getTenantId, oneTenantService.authorizedTenantIdSet());
            if (null != spuId) {
                queryWrapper.eq(OneSku::getSpuId, spuId);
            }
            return ResponseEntity.ok(service.list(queryWrapper));
        } catch (Exception e) {
            log.error("get sku list error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新增sku信息
     *
     * @return sku信息
     * @apiNote 新增sku信息
     */
    @OperateLog(operateModule = "sku模块", operateType = "新增sku信息", operateDesc = "新增sku信息")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody OneSku sku) {
        try {
            oneTenantService.authenticate(sku.getTenantId());
            service.save(sku);
        } catch (Exception e) {
            log.error("save sku extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(sku);
    }

    /**
     * 更新sku信息
     *
     * @return sku信息
     * @apiNote 更新sku信息
     */
    @OperateLog(operateModule = "sku模块", operateType = "更新sku信息", operateDesc = "更新sku信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneSku sku) {
        try {
            oneTenantService.authenticate(sku.getTenantId());
            service.updateById(sku);
        } catch (Exception e) {
            log.error("update sku extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(sku);
    }

    /**
     * 删除sku信息
     *
     * @return sku信息
     * @apiNote 删除sku信息
     */
    @OperateLog(operateModule = "sku模块", operateType = "删除sku信息", operateDesc = "删除sku信息")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneSku sku) {
        try {
            sku = service.getById(sku.getId());
            oneTenantService.authenticate(sku.getTenantId());
            sku.setDeleteFlag(true);
            service.updateById(sku);
        } catch (Exception e) {
            log.error("remove sku extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(sku);
    }
}
