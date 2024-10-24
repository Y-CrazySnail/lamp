package com.yeem.lamp.presentation.controller.web;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.application.service.web.PackageWebAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/package")
public class PackageWebController {

    @Autowired
    private PackageWebAppService packageAppService;

    /**
     * 列表查询
     *
     * @return 套餐列表信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(packageAppService.list());
        } catch (Exception e) {
            log.error("list查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("list查询失败");
        }
    }
}
