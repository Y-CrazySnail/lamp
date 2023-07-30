package com.yeem.zero.controller;

import com.yeem.common.utils.OauthUtils;
//import com.yeem.zero.service.IZeroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/zero-auth")
public class ZeroAuthController {
//
//    @Autowired
//    private IZeroAuthService zeroAuthService;

    @PostMapping("/login")
    public ResponseEntity<Object> login() {
        String response;
        Object  ob = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
//            response = zeroAuthService.signupOrLogin(wxLoginDTO);
        } catch (Exception e) {
            log.error("zero login error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
//        return ResponseEntity.ok(response);
        return null;
    }
}
