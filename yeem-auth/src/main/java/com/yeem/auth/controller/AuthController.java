package com.yeem.auth.controller;

import cn.hutool.http.HttpStatus;
import com.yeem.common.dto.WechatMiniProgramDTO;
import com.yeem.auth.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/wechat-mini-program")
    public ResponseEntity<Object> miniProgram(@RequestBody WechatMiniProgramDTO wechatMiniProgramDTO) {
        try {
            WechatMiniProgramDTO wechatMiniProgramDTORes = authService.miniProgram(wechatMiniProgramDTO);
            return ResponseEntity.ok(wechatMiniProgramDTORes);
        } catch (Exception e) {
            log.error("auth wechat mini program error:", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("auth wechat mini program error");
        }
    }
}
