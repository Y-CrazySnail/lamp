package com.yeem.one.controller.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.OauthUtils;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneTenant;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-租户
 */
@Slf4j
@RestController
@RequestMapping("/manage/tenant")
public class OneTenantController {

    @Autowired
    private IOneTenantService oneTenantService;

    /**
     * 分页查询
     *
     * @param current 当前页
     * @param size    页码
     * @return 租户分页
     */
    @GetMapping("page")
    public ResponseEntity<IPage<OneTenant>> getPage(@RequestParam("current") Integer current,
                                                    @RequestParam("size") Integer size,
                                                    @RequestParam(value = "tenantName", required = false) String tenantName,
                                                    @RequestParam(value = "tenantPhone", required = false) String tenantPhone,
                                                    @RequestParam(value = "tenantEmail", required = false) String tenantEmail) {
        if (null == current) {
            current = 1;
        }
        if (null == size) {
            size = 10;
        }
        IPage<OneTenant> page = new Page<>(current, size);
        QueryWrapper<OneTenant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        if (!StrUtil.isEmpty(tenantName)) {
            queryWrapper.like("tenant_name", tenantName);
        }
        if (!StrUtil.isEmpty(tenantPhone)) {
            queryWrapper.like("tenant_phone", tenantPhone);
        }
        if (!StrUtil.isEmpty(tenantEmail)) {
            queryWrapper.like("tenant_email", tenantEmail);
        }
        try {
            return ResponseEntity.ok(oneTenantService.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("get tenant page error:", e);
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
    public ResponseEntity<OneTenant> getById(@RequestParam(value = "id", required = false) Long id) {
        try {
            return ResponseEntity.ok(oneTenantService.getById(id));
        } catch (Exception e) {
            log.error("get tenant by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 获取所有
     *
     * @return 租户信息
     */
    @GetMapping(value = "getAll")
    public ResponseEntity<List<OneTenant>> getAll() {
        try {
            String username = OauthUtils.getUsername();
            return ResponseEntity.ok(oneTenantService.listByUsername(username));
        } catch (Exception e) {
            log.error("get tenant by id error:", e);
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
    public ResponseEntity<Object> save(@RequestBody OneTenant tenant) {
        try {
            oneTenantService.save(tenant);
        } catch (Exception e) {
            log.error("save tenant extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(tenant);
    }

    /**
     * 更新用户信息
     *
     * @return 租户信息
     * @apiNote 更新用户信息
     */
    @OperateLog(operateModule = "租户模块", operateType = "更新租户信息", operateDesc = "更新租户信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneTenant tenant) {
        try {
            oneTenantService.updateById(tenant);
        } catch (Exception e) {
            log.error("update tenant extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(tenant);
    }

    /**
     * 删除用户信息
     *
     * @return 租户信息
     * @apiNote 删除用户信息
     */
    @OperateLog(operateModule = "租户模块", operateType = "删除租户信息", operateDesc = "删除租户信息")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneTenant tenant) {
        try {
            tenant.setDeleteFlag(true);
            oneTenantService.updateById(tenant);
        } catch (Exception e) {
            log.error("remove tenant extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(tenant);
    }
}
