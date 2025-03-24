package com.lamp.controller.web;

import com.lamp.security.LocalAuthInterceptor;
import com.lamp.service.web.LampMemberService;
import com.lamp.service.web.LampProductService;
import com.lamp.service.web.LampSPUService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/web/lamp-spu")
public class LampSPUController {

    @Autowired
    private LampProductService productService;

    @Autowired
    private LampMemberService memberService;

    @Autowired
    private LampSPUService spuService;

    @GetMapping(value = "list")
    public ResponseEntity<Object> list() {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(spuService.listByMemberId(memberId));
        } catch (Exception e) {
            log.error("查询产品失败:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询产品失败");
        }
    }

}
