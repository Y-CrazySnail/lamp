package com.yeem.car.controller.wechat;

import cn.hutool.http.HttpStatus;
import com.yeem.car.service.wechat.WechatBCDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wechat/dictionary")
public class BCDictionaryWechatController {

    @Autowired
    @Qualifier(value = "wechatBCDictionaryService")
    private WechatBCDictionaryService dictionaryService;

    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(value = "tenantNo") String tenantNo,
                                                 @RequestParam(value = "dictType") String dictType) {
        try {
            return ResponseEntity.ok(dictionaryService.list(dictType, tenantNo));
        } catch (Exception e) {
            log.error("查询字典失败：", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询字典失败");
        }
    }
}
