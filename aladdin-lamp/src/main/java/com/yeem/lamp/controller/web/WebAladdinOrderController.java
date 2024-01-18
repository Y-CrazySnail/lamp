package com.yeem.lamp.controller.web;

import cn.hutool.http.HttpStatus;
import com.yeem.lamp.entity.AladdinOrder;
import com.yeem.lamp.service.IAladdinPackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/web/order")
public class WebAladdinOrderController {

    @Autowired
    private IAladdinPackageService aladdinPackageService;

    /**
     * 下单
     *
     * @return 下单结果
     */
    @PostMapping("/order")
    public ResponseEntity<Object> order(@RequestBody AladdinOrder aladdinOrder) {
        try {
            return ResponseEntity.ok(aladdinPackageService.list());
        } catch (Exception e) {
            log.error("下单", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("下单");
        }
    }

    /**
     * 完成
     *
     * @return 完成
     */
    @PostMapping("/finish")
    public ResponseEntity<Object> finish(@RequestBody AladdinOrder aladdinOrder) {
        try {
            return ResponseEntity.ok(aladdinPackageService.list());
        } catch (Exception e) {
            log.error("下单", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("下单");
        }
    }
}
