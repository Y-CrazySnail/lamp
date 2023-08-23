package com.yeem.zero.controller.wechat;

import com.yeem.common.aspect.log.OperateLog;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序-用户信息
 */
@Slf4j
@RestController
@RequestMapping("/wechat-zero-user")
public class ZeroUserController {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    /**
     * 查询用户信息
     *
     * @return 用户信息
     * @apiNote 查询用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "查询用户信息", operateDesc = "查询用户信息")
    @GetMapping("get")
    public ResponseEntity<Object> get() {
        String username = OauthUtils.getUsername();
        if (StringUtils.isEmpty(username)) {
            log.error("get username error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("get username error");
        }
        ZeroUserExtra userExtra;
        try {
            userExtra = zeroUserExtraService.get(username);
        } catch (Exception e) {
            log.error("get user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(userExtra);
    }

    /**
     * 更新用户信息
     *
     * @return 用户信息
     * @apiNote 更新用户信息
     */
    @OperateLog(operateModule = "用户模块", operateType = "更新用户信息", operateDesc = "更新用户信息")
    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroUserExtra entity) {
        ZeroUserExtra zeroUserExtra;
        try {
            zeroUserExtra = zeroUserExtraService.update(entity);
        } catch (Exception e) {
            log.error("update user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(zeroUserExtra);
    }
}
