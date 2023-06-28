package com.snail.auth.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.auth.dto.WxLoginDTO;
import com.snail.auth.dto.WxLoginResponse;
import com.snail.auth.service.IZeroAuthService;
import com.snail.auth.service.impl.ZeroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/zero-auth")
public class ZeroAuthController {

    @Autowired
    private IZeroAuthService zeroAuthService;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody WxLoginDTO wxLoginDTO) {
        try {
            zeroAuthService.signupOrLogin(wxLoginDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(httpResponse.body());
    }
}
