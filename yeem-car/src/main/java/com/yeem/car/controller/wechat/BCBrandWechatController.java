package com.yeem.car.controller.wechat;

import cn.hutool.http.HttpStatus;
import com.yeem.car.service.IBCBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wechat/brand")
public class BCBrandWechatController {

    @Autowired
    private IBCBrandService brandService;

    @GetMapping("/list")
    public ResponseEntity<Object> listCarBrand() {
        try {
            return ResponseEntity.ok(brandService.listForWechat());
        } catch (Exception e) {
            log.error("查询汽车品牌失败：", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询汽车品牌失败");
        }
    }
}
