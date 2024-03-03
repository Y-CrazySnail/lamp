package com.yeem.one.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneCategory;
import com.yeem.one.entity.OneSku;
import com.yeem.one.entity.OneSpu;
import com.yeem.one.entity.OneStore;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneCategoryService;
import com.yeem.one.service.IOneSpuService;
import com.yeem.one.service.IOneStoreService;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端-SPU
 */
@Slf4j
@RestController
@RequestMapping("/manage/spu")
public class OneSpuController {

    @Autowired
    private IOneSpuService service;
    @Autowired
    private IOneTenantService oneTenantService;
    @Autowired
    private IOneStoreService oneStoreService;
    @Autowired
    private IOneCategoryService oneCategoryService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return SPU分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneSpu>> getPage(@RequestParam("current") Integer current,
                                                 @RequestParam("size") Integer size,
                                                 @RequestParam(value = "storeId", required = false) Long storeId,
                                                 @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                 @RequestParam(value = "spuName", required = false) String spuName) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<OneSpu> page = new Page<>(current, size);
        LambdaQueryWrapper<OneSpu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneSpu::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.in(OneSpu::getTenantId, oneTenantService.authorizedTenantIdSet());
        if (!StringUtils.isEmpty(storeId)) {
            queryWrapper.eq(OneSpu::getStoreId, storeId);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            queryWrapper.eq(OneSpu::getCategoryId, categoryId);
        }
        if (!StringUtils.isEmpty(spuName)) {
            queryWrapper.like(OneSpu::getSpuName, spuName);
        }
        try {
            page = service.page(page, queryWrapper);
            for (OneSpu record : page.getRecords()) {
                OneStore store = oneStoreService.getById(record.getStoreId());
                record.setStoreName(store.getStoreName());
                OneCategory category = oneCategoryService.getById(record.getCategoryId());
                record.setCategoryName(category.getCategoryName());
            }
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            log.error("get spu page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取
     *
     * @param id 租户ID
     * @return SPU信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneSpu> getById(@RequestParam(value = "id", required = false) Long id) {
        try {
            OneSpu spu = service.getById(id);
            oneTenantService.authenticate(spu.getTenantId());
            return ResponseEntity.ok(spu);
        } catch (Exception e) {
            log.error("get spu by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新增SPU信息
     *
     * @return SPU信息
     * @apiNote 新增SPU信息
     */
    @OperateLog(operateModule = "SPU模块", operateType = "新增SPU信息", operateDesc = "新增SPU信息")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody OneSpu spu) {
        try {
            oneTenantService.authenticate(spu.getTenantId());
            service.save(spu);
        } catch (Exception e) {
            log.error("save spu extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(spu);
    }

    /**
     * 更新SPU信息
     *
     * @return SPU信息
     * @apiNote 更新SPU信息
     */
    @OperateLog(operateModule = "SPU模块", operateType = "更新SPU信息", operateDesc = "更新SPU信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneSpu spu) {
        try {
            oneTenantService.authenticate(spu.getTenantId());
            service.updateById(spu);
        } catch (Exception e) {
            log.error("update spu extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(spu);
    }

    /**
     * 删除SPU信息
     *
     * @return SPU信息
     * @apiNote 删除SPU信息
     */
    @OperateLog(operateModule = "SPU模块", operateType = "删除SPU信息", operateDesc = "删除SPU信息")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneSpu spu) {
        try {
            spu = service.getById(spu.getId());
            oneTenantService.authenticate(spu.getTenantId());
            spu.setDeleteFlag(true);
            service.updateById(spu);
        } catch (Exception e) {
            log.error("remove spu extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(spu);
    }
}
