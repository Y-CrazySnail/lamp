package com.yeem.one.controller.wechat;

import com.yeem.one.entity.OneSku;
import com.yeem.one.entity.OneSpu;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.IOneSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信小程序端-SPU
 */
@Slf4j
@RestController
@RequestMapping("/wechat/spu")
public class OneWechatSpuController {

    @Autowired
    private IOneSpuService service;

    /**
     * 获取所有
     *
     * @return SPU信息
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<OneSpu>> list(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                             @RequestParam(value = "spuName", required = false) String spuName) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            OneSpu spu = new OneSpu();
            spu.setCategoryId(categoryId);
            spu.setSpuName(spuName);
            spu.setTenantId(tenantId);
            return ResponseEntity.ok(service.list(spu));
        } catch (Exception e) {
            log.error("list spu error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 获取所有
     *
     * @return SPU信息
     */
    @GetMapping(value = "get")
    public ResponseEntity<OneSpu> get(@RequestParam(value = "id") Long id) {
        try {
            Long tenantId = WechatAuthInterceptor.getTenantId();
            return ResponseEntity.ok(service.getWithOther(id, tenantId));
        } catch (Exception e) {
            log.error("get spu by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
