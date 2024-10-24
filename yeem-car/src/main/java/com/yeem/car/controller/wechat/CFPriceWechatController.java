package com.yeem.car.controller.wechat;

import com.yeem.car.service.wechat.WechatCFPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wechat/price")
public class CFPriceWechatController {

    @Autowired
    private WechatCFPriceService priceService;

    /**
     * 小程序登录
     *
     * @return 登录信息
     * @apiNote 小程序登录
     */
    @GetMapping("get")
    public ResponseEntity<Object> get(@RequestParam(value = "tenantNo") String tenantNo,
                                       @RequestParam(value = "productType") String productType,
                                       @RequestParam(value = "productNo") String productNo,
                                       @RequestParam(value = "levelNo") String levelNo) {
        try {
            return ResponseEntity.ok(priceService.get(tenantNo, productType, productNo, levelNo));
        } catch (Exception e) {
            log.error("wx login api error：", e);
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
