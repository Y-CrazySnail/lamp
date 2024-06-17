package com.yeem.lamp.presentation.controller.web;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.presentation.interceptor.LocalAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/service")
public class ServiceWebController {

    @Autowired
    private ServiceAppService serviceAppService;

    /**
     * 列表查询
     *
     * @return 会员信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            Long memberId = LocalAuthInterceptor.getMemberId();
            return ResponseEntity.ok(serviceAppService.listServiceByMemberId(memberId));
        } catch (Exception e) {
            log.error("list查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("list查询失败");
        }
    }
}
