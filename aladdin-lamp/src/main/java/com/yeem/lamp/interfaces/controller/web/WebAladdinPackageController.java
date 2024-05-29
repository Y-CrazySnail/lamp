package com.yeem.lamp.interfaces.controller.web;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.application.service.AladdinPackageAppService;
import com.yeem.lamp.domain.service.IAladdinPackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web/package")
public class WebAladdinPackageController {

    @Autowired
    private IAladdinPackageService aladdinPackageService;

    @Autowired
    private AladdinPackageAppService aladdinPackageAppService;
    /**
     * 列表查询
     *
     * @return 会员信息
     */
    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        try {
            return ResponseEntity.ok(aladdinPackageService.list());
        } catch (Exception e) {
            log.error("list查询失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("list查询失败");
        }
    }
}
