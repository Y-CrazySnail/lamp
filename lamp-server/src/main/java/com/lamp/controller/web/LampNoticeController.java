package com.lamp.controller.web;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.lamp.entity.LampOrder;
import com.lamp.security.LocalAuthInterceptor;
import com.lamp.service.web.LampNoticeService;
import com.lamp.service.web.LampOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/lamp-notice")
public class LampNoticeController {

    @Autowired
    private LampNoticeService noticeService;

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(noticeService.listByMemberId(memberId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询通知失败");
        }
    }
}
