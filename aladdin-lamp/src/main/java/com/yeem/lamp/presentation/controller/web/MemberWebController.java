package com.yeem.lamp.presentation.controller.web;

import com.yeem.lamp.application.dto.TokenDTO;
import com.yeem.lamp.application.service.MemberAppService;
import com.yeem.lamp.presentation.request.LoginRequest;
import com.yeem.lamp.presentation.interceptor.LocalAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/member")
public class MemberWebController {

    @Autowired
    private MemberAppService memberAppService;

    /**
     * 登录
     *
     * @param loginRequest loginRequest
     * @return 登录token
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            TokenDTO tokenDTO = memberAppService.login(loginRequest);
            return ResponseEntity.ok(tokenDTO);
        } catch (Exception e) {
            log.error("login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录失败");
        }
    }

    /**
     * 根据ID查询
     *
     * @return 会员信息
     */
    @GetMapping("/getById")
    public ResponseEntity<Object> getById() {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            if (null == id) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("按id查询 id为空");
            }
            return ResponseEntity.ok(memberAppService.getByIdWithService(id));
        } catch (Exception e) {
            log.error("getById方法", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("按id查询失败");
        }
    }
}
