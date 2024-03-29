package com.yeem.one.controller.wechat;

import cn.hutool.core.util.StrUtil;
import com.yeem.one.entity.OneUser;
import com.yeem.one.log.OperateLog;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序端-用户
 */
@Slf4j
@RestController
@RequestMapping("/wechat/user")
public class OneWechatUserController {

    @Autowired
    private IOneUserService service;

    /**
     * 根据ID获取
     *
     * @return 用户信息
     */
    @GetMapping(value = "get")
    public ResponseEntity<OneUser> get() {
        try {
            Long id = WechatAuthInterceptor.getUserId();
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            log.error("get user error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 登录
     *
     * @param user 用户信息
     * @return 登陆后用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "用户登录", operateDesc = "用户注册、登录")
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody OneUser user) {
        try {
            if (StrUtil.isEmpty(user.getWechatCode())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wechat code is null");
            }
            if (StrUtil.isEmpty(user.getWechatAppId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wechat app id is null");
            }
            user = service.login(user);
        } catch (Exception e) {
            log.error("login error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }
}
