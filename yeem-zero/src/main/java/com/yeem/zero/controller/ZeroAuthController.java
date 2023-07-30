package com.yeem.zero.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.dto.WechatMiniProgramDTO;
import com.yeem.common.utils.OauthUtils;
//import com.yeem.zero.service.IZeroAuthService;
import com.yeem.zero.service.IZeroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/zero-auth")
public class ZeroAuthController {

    @Autowired
    private IZeroAuthService zeroAuthService;

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
