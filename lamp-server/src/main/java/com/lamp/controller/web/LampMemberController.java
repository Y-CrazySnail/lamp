package com.lamp.controller.web;

import com.lamp.entity.LampMember;
import com.lamp.presentation.interceptor.LocalAuthInterceptor;
import com.lamp.service.web.LampMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/lamp-member")
public class LampMemberController {

    @Autowired
    private LampMemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LampMember member) {
        try {
            return ResponseEntity.ok(memberService.login(member));
        } catch (Exception e) {
            log.error("登录失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录失败");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(@RequestBody LampMember member) {
        try {
            memberService.signUp(member);
            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            log.error("注册失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get() {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(memberService.getById(id));
        } catch (Exception e) {
            log.error("按id查询失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("按id查询失败");
        }
    }
}
