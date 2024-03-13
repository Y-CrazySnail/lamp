package com.yeem.one.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneStore;
import com.yeem.one.fs.entity.SysFS;
import com.yeem.one.fs.service.ISysFSService;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneStoreService;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理端-店铺
 */
@Slf4j
@RestController
@RequestMapping("/manage/store")
public class OneStoreController {

    @Autowired
    private IOneStoreService service;
    @Autowired
    private IOneTenantService oneTenantService;
    @Resource(name = "COSSysFSServiceImpl")
    private ISysFSService sysFSService;
    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 店铺分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneStore>> getPage(@RequestParam(value = "current",defaultValue = "1") Integer current,
                                                   @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                   @RequestParam(value = "storeName", required = false) String storeName) {
        IPage<OneStore> page = new Page<>(current, size);
        LambdaQueryWrapper<OneStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OneStore::getDeleteFlag, Constant.BOOLEAN_FALSE);
        lambdaQueryWrapper.in(OneStore::getTenantId, oneTenantService.authorizedTenantIdSet());
        if (!StrUtil.isEmpty(storeName)) {
            lambdaQueryWrapper.like(OneStore::getStoreName, storeName);
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
     * @return 店铺信息
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
     * 获取所有
     *
     * @return 店铺信息
     */
    @GetMapping(value = "getAll")
    public ResponseEntity<List<OneStore>> getAll() {
        try {
            LambdaQueryWrapper<OneStore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OneStore::getDeleteFlag, Constant.BOOLEAN_FALSE);
            lambdaQueryWrapper.in(OneStore::getTenantId, oneTenantService.authorizedTenantIdSet());
            return ResponseEntity.ok(service.list(lambdaQueryWrapper));
        } catch (Exception e) {
            log.error("get tenant by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新增店铺信息
     *
     * @return 店铺信息
     * @apiNote 新增店铺信息
     */
    @OperateLog(operateModule = "店铺模块", operateType = "新增店铺信息", operateDesc = "新增店铺信息")
    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody OneStore store) {
        try {
            oneTenantService.authenticate(store.getTenantId());
            service.save(store);
        } catch (Exception e) {
            log.error("save store extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(store);
    }

    /**
     * 更新店铺信息
     *
     * @return 店铺信息
     * @apiNote 更新店铺信息
     */
    @OperateLog(operateModule = "店铺模块", operateType = "更新店铺信息", operateDesc = "更新店铺信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneStore store) {
        try {
            oneTenantService.authenticate(store.getTenantId());
            service.updateById(store);
        } catch (Exception e) {
            log.error("update store extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(store);
    }

    /**
     * 删除店铺信息
     *
     * @return 店铺信息
     * @apiNote 删除店铺信息
     */
    @OperateLog(operateModule = "店铺模块", operateType = "删除店铺信息", operateDesc = "删除店铺信息")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneStore store) {
        try {
            store = service.getById(store.getId());
            oneTenantService.authenticate(store.getTenantId());
            store.setDeleteFlag(true);
            service.updateById(store);
        } catch (Exception e) {
            log.error("remove store extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(store);
    }

    /**
     * 上传店铺文件
     *
     * @param file 文件
     * @return URL
     */
    @PostMapping("upload")
    public ResponseEntity<Object> upload(@RequestPart("file") MultipartFile file) {
        try {
            SysFS sysFS = new SysFS("store");
            String url = sysFSService.upload(sysFS, file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            log.error("upload store file error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
