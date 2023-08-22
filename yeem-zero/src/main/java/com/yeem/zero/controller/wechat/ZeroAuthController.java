package com.yeem.zero.controller.wechat;

import com.yeem.common.dto.WechatMiniProgramDTO;
import com.yeem.zero.service.IZeroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序认证授权
 */
@Slf4j
@RestController
@RequestMapping("/wechat-zero-auth")
public class ZeroAuthController {

    @Autowired
    private IZeroAuthService zeroAuthService;

    /**
     * 微信小程序登录
     *
     * @param wechatMiniProgramDTO 微信小程序DTO
     * @return 登录响应信息
     * @apiNote 微信小程序登录
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody WechatMiniProgramDTO wechatMiniProgramDTO) {
        try {
            String response = zeroAuthService.wechatMiniProgramLogin(wechatMiniProgramDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("login error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("login error");
        }
    }
}
