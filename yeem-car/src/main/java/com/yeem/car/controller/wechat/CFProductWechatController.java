package com.yeem.car.controller.wechat;

import com.yeem.car.service.wechat.WechatCFProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/wechat/product")
public class CFProductWechatController {

    @Autowired
    private WechatCFProductService productService;

    /**
     * 小程序登录
     *
     * @return 登录信息
     * @apiNote 小程序登录
     */
    @GetMapping("list")
    public ResponseEntity<Object> list(@RequestParam(value = "tenantNo") String tenantNo) {
        try {
            return ResponseEntity.ok(productService.list(tenantNo));
        } catch (Exception e) {
            log.error("wx login api error：", e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
