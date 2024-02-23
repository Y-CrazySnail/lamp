package com.yeem.one.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneStore;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端-店铺
 */
@Slf4j
@RestController
@RequestMapping("/manage/one/store")
public class OneStoreController {

    @Autowired
    private IOneStoreService service;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 店铺分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneStore>> getPage(@RequestParam("current") Integer current,
                                                   @RequestParam("size") Integer size,
                                                   @RequestParam(value = "name", required = false) String name) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        IPage<OneStore> page = new Page<>(current, size);
        LambdaQueryWrapper<OneStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OneStore::getDeleteFlag, Constant.BOOLEAN_FALSE);
        if (!StringUtils.isEmpty(name)) {
            lambdaQueryWrapper.like(OneStore::getStoreName, name);
        }
        try {
            return ResponseEntity.ok(service.page(page, lambdaQueryWrapper));
        } catch (Exception e) {
            log.error("get store page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取
     *
     * @param id 租户ID
     * @return 租户信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneStore> getById(@RequestParam(value = "id", required = false) Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            log.error("get store by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新增用户信息
     *
     * @return 租户信息
     * @apiNote 新增用户信息
     */
    @OperateLog(operateModule = "租户模块", operateType = "新增租户信息", operateDesc = "新增租户信息")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody OneStore store) {
        try {
            service.save(store);
        } catch (Exception e) {
            log.error("save store extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(store);
    }

    /**
     * 更新用户信息
     *
     * @return 租户信息
     * @apiNote 更新用户信息
     */
    @OperateLog(operateModule = "租户模块", operateType = "更新租户信息", operateDesc = "更新租户信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneStore store) {
        try {
            service.updateById(store);
        } catch (Exception e) {
            log.error("update store extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(store);
    }

    /**
     * 删除用户信息
     *
     * @return 租户信息
     * @apiNote 删除用户信息
     */
    @OperateLog(operateModule = "租户模块", operateType = "删除租户信息", operateDesc = "删除租户信息")
    @PutMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneStore store) {
        try {
            store.setDeleteFlag(true);
            service.updateById(store);
        } catch (Exception e) {
            log.error("remove store extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(store);
    }
}
