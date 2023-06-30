package com.snail.auth.controller;

import com.snail.auth.dto.WxLoginDTO;
import com.snail.zero.entity.UserExtra;
import com.snail.auth.service.IZeroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/zero-auth")
public class ZeroAuthController {

    @Autowired
    private IZeroAuthService zeroAuthService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody WxLoginDTO wxLoginDTO) {
        String response;
        try {
            response = zeroAuthService.signupOrLogin(wxLoginDTO);
        } catch (Exception e) {
            log.error("zero login error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
