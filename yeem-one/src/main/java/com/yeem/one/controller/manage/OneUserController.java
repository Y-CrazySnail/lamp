package com.yeem.one.controller.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.one.config.Constant;
import com.yeem.one.entity.OneUser;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneAddressService;
import com.yeem.one.service.IOneTenantService;
import com.yeem.one.service.IOneUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端-用户
 */
@Slf4j
@RestController
@RequestMapping("/manage/user")
public class OneUserController {

    @Autowired
    private IOneUserService service;
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
    public ResponseEntity<IPage<OneUser>> getPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                  @RequestParam(value = "nickName", required = false) String nickName,
                                                  @RequestParam(value = "userPhone", required = false) String userPhone) {
        IPage<OneUser> page = new Page<>(current, size);
        LambdaQueryWrapper<OneUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneUser::getDeleteFlag, Constant.BOOLEAN_FALSE);
        queryWrapper.in(OneUser::getTenantId, oneTenantService.authorizedTenantIdSet());
        if (StrUtil.isNotEmpty(nickName)) {
            queryWrapper.like(OneUser::getNickName, nickName);
        }
        if (StrUtil.isNotEmpty(userPhone)) {
            queryWrapper.like(OneUser::getUserPhone, userPhone);
        }
        try {
            return ResponseEntity.ok(service.page(page, queryWrapper));
        } catch (Exception e) {
            log.error("get user page error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取
     *
     * @param id 用户 ID
     * @return 用户信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneUser> getById(@RequestParam(value = "id") Long id) {
        try {
            OneUser user = service.getByIdWithAddress(id);
            oneTenantService.authenticate(user.getTenantId());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("get user by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 获取所有
     *
     * @return 用户信息
     */
    @GetMapping(value = "getAll")
    public ResponseEntity<List<OneUser>> getAll() {
        try {
            LambdaQueryWrapper<OneUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OneUser::getDeleteFlag, Constant.BOOLEAN_FALSE);
            queryWrapper.in(OneUser::getTenantId, oneTenantService.authorizedTenantIdSet());
            return ResponseEntity.ok(service.list(queryWrapper));
        } catch (Exception e) {
            log.error("get user list error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 更新用户信息
     *
     * @return 用户信息
     * @apiNote 更新用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "更新用户信息", operateDesc = "更新用户信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody OneUser user) {
        try {
            oneTenantService.authenticate(user.getTenantId());
            service.updateById(user);
        } catch (Exception e) {
            log.error("update user info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 删除用户信息
     *
     * @return 用户信息
     * @apiNote 删除用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "删除用户信息", operateDesc = "删除用户信息")
    @DeleteMapping("remove")
    public ResponseEntity<Object> remove(@RequestBody OneUser user) {
        try {
            user = service.getById(user.getId());
            oneTenantService.authenticate(user.getTenantId());
            user.setDeleteFlag(true);
            service.updateById(user);
        } catch (Exception e) {
            log.error("remove user info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }
}
