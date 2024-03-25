package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneUser;
import com.yeem.one.log.OperateLog;
import com.yeem.one.service.IOneTenantService;
import com.yeem.one.service.IOneUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端-用户
 */
@Slf4j
@RestController
@RequestMapping("/wechat/user")
public class OneWechatUserController {

    @Autowired
    private IOneUserService service;
    @Autowired
    private IOneTenantService oneTenantService;

    /**
     * 根据ID获取
     *
     * @param id 用户 ID
     * @return 用户信息
     */
    @GetMapping(value = "getById")
    public ResponseEntity<OneUser> getById(@RequestParam(value = "id") Long id) {
        try {
            OneUser user = service.getByIdWithOther(id);
            oneTenantService.authenticate(user.getTenantId());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("get user by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @OperateLog(operateModule = "用户模块", operateType = "用户登录", operateDesc = "用户注册、登录")
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody OneUser user) {
        try {
            // 登录
            user = service.login(user);
        } catch (Exception e) {
            log.error("login error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }
}
