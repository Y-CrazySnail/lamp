package com.snail.zero.controller;

import com.snail.zero.entity.ZeroUserExtra;
import com.snail.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/zero-user")
public class ZeroUserController {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @GetMapping("info")
    public ResponseEntity<Object> info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (StringUtils.isEmpty(username)) {
            log.error("get username error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("get username error");
        }
        ZeroUserExtra userExtra;
        try {
            userExtra = zeroUserExtraService.info(username);
        } catch (Exception e) {
            log.error("get user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(userExtra);
    }
}
