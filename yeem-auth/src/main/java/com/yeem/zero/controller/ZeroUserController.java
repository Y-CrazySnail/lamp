package com.yeem.zero.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.utils.OauthUtils;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/zero-user")
public class ZeroUserController extends BaseController<ZeroUserExtra> {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @GetMapping("get")
    public ResponseEntity<Object> get() {
        String username = OauthUtils.getUsername();
        if (StringUtils.isEmpty(username)) {
            log.error("get username error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("get username error");
        }
        ZeroUserExtra userExtra;
        try {
            userExtra = zeroUserExtraService.get(username);
        } catch (Exception e) {
            log.error("get user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(userExtra);
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody ZeroUserExtra entity) {
        ZeroUserExtra zeroUserExtra;
        try {
            zeroUserExtra = zeroUserExtraService.update(entity);
        } catch (Exception e) {
            log.error("update user extra info error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(zeroUserExtra);
    }
}
