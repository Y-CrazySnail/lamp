package com.yeem.lamp.controller.web;

import com.yeem.lamp.entity.LampMember;
import com.yeem.lamp.presentation.interceptor.LocalAuthInterceptor;
import com.yeem.lamp.service.LampMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
