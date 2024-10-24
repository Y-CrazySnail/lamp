package com.yeem.lamp.presentation.controller.web;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.lamp.application.service.web.ServiceWebAppService;
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
    private ServiceWebAppService serviceAppService;

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

    /**
     * 列表查询
     *
     * @return apple信息
     */
    @GetMapping("/apple")
    public ResponseEntity<Object> apple() {
        try {
            HttpResponse response = HttpUtil.createRequest(Method.GET, "https://xyid.me/shareapi/aladdin").execute();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            return ResponseEntity.ok(jsonNode.get("accounts").get(0));
        } catch (Exception e) {
            log.error("list查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("list查询失败");
        }
    }

}
