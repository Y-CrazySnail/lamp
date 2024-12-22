package com.lamp.controller.web;

import com.lamp.entity.LampService;
import com.lamp.presentation.interceptor.LocalAuthInterceptor;
import com.lamp.service.web.LampServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/web/lamp-service")
public class LampServiceController {

    @Autowired
    private LampServiceService lampServiceService;

    @GetMapping(value = "list")
    public ResponseEntity<Object> list() {
        try {
            Long id = LocalAuthInterceptor.getMemberId();
            List<LampService> list = lampServiceService.listByMemberId(id);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("查询失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询失败");
        }
    }
}
