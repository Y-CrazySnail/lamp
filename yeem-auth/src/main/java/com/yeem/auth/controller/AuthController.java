package com.yeem.auth.controller;

import com.yeem.auth.dto.WxLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/wechat")
    public ResponseEntity<Object> wechat(@RequestBody WxLoginDTO wxLoginDTO) {
        return null;
    }
}
