package com.snail.zero.controller;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.zero.entity.WxLoginResponse;
import com.snail.zero.entity.ZeroLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/zero-login")
public class ZeroLoginController {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody ZeroLoginInfo zeroLoginInfo) {
        String code = zeroLoginInfo.getCode();
        String appId = "wx9cb140f2eacb6870";
        String appSecret = "b873d35be8357186fc8f90c48edb24a9";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        String res = HttpRequest.get(url).execute().body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            WxLoginResponse wxLoginResponse = objectMapper.readValue(res, WxLoginResponse.class);
            log.info("openid:{}", wxLoginResponse.getOpenid());
            log.info("session_key:{}", wxLoginResponse.getSession_key());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("这应该是个token");
    }
}
