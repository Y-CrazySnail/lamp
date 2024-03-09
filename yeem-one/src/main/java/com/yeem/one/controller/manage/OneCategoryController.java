package com.yeem.one.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneCategory;
import com.yeem.one.entity.OneStore;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneCategoryService;
import com.yeem.one.service.IOneStoreService;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-分类
 */
@Slf4j
@RestController
@RequestMapping("/manage/category")
public class OneCategoryController {

    @Autowired
    private IOneCategoryService service;
    @Autowired
    private IOneTenantService oneTenantService;
    @Autowired
    private IOneStoreService oneStoreService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 分类分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneCategory>> page(@RequestParam("current") Integer current,
                                                   @RequestParam("size") Integer size,
                                                   @RequestParam(value = "storeId", required = false) Long storeId,
                                                   @RequestParam(value = "categoryName", required = false) String categoryName) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<OneCategory> page = new Page<>(current, size);
        LambdaQueryWrapper<OneCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneCategory::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.in(OneCategory::getTenantId, oneTenantService.authorizedTenantIdSet());
        if (!StringUtils.isEmpty(storeId)) {
            queryWrapper.eq(OneCategory::getStoreId, storeId);
        }
        if (!StringUtils.isEmpty(categoryName)) {
            queryWrapper.like(OneCategory::getCategoryName, categoryName);
        }
        try {
            page = service.page(page, queryWrapper);
            for (OneCategory record : page.getRecords()) {
                OneStore store = oneStoreService.getById(record.getStoreId());
                record.setStoreName(store.getStoreName());
            }
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            log.error("get category page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取
     *
     * @param id 租户ID
     * @return 分类信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneCategory> getById(@RequestParam(value = "id", required = false) Long id) {
        try {
            OneCategory category = service.getById(id);
            oneTenantService.authenticate(category.getTenantId());
            OneStore store = oneStoreService.getById(category.getStoreId());
            category.setStoreName(store.getStoreName());
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            log.error("get category by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * 获取所有
     *
     * @return 分类信息
     */
    @GetMapping(value = "getAll")
    public ResponseEntity<List<OneCategory>> getAll() {
        try {
            LambdaQueryWrapper<OneCategory> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OneCategory::getDeleteFlag, Constant.BOOLEAN_FALSE);
            queryWrapper.in(OneCategory::getTenantId, oneTenantService.authorizedTenantIdSet());
            return ResponseEntity.ok(service.list(queryWrapper));
        } catch (Exception e) {
            log.error("get category by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新增分类信息
     *
     * @return 分类信息
     * @apiNote 新增分类信息
     */
    @OperateLog(operateModule = "分类模块", operateType = "新增分类信息", operateDesc = "新增分类信息")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody OneCategory category) {
        try {
            oneTenantService.authenticate(category.getTenantId());
            service.save(category);
        } catch (Exception e) {
            log.error("save category extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(category);
    }

    /**
     * 更新分类信息
     *
     * @return 分类信息
     * @apiNote 更新分类信息
     */
    @OperateLog(operateModule = "分类模块", operateType = "更新分类信息", operateDesc = "更新分类信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneCategory category) {
        try {
            oneTenantService.authenticate(category.getTenantId());
            service.updateById(category);
        } catch (Exception e) {
            log.error("update category extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(category);
    }

    /**
     * 删除分类信息
     *
     * @return 分类信息
     * @apiNote 删除分类信息
     */
    @OperateLog(operateModule = "分类模块", operateType = "删除分类信息", operateDesc = "删除分类信息")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneCategory category) {
        try {
            category = service.getById(category.getId());
            oneTenantService.authenticate(category.getTenantId());
            category.setDeleteFlag(true);
            service.updateById(category);
        } catch (Exception e) {
            log.error("remove category extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(category);
    }
}
