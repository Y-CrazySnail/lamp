package com.yeem.one.controller.wechat;

import com.yeem.fss.service.ISysFSService;
import com.yeem.one.entity.OneSpu;
import com.yeem.one.service.IOneCategoryService;
import com.yeem.one.service.IOneSpuService;
import com.yeem.one.service.IOneStoreService;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Autowired
    private IOneTenantService tenantService;
    @Autowired
    private IOneStoreService storeService;
    @Autowired
    private IOneCategoryService categoryService;
    @Resource(name = "COSSysFSServiceImpl")
    private ISysFSService sysFSService;

    /**
     * 获取所有
     *
     * @return SPU信息
     */
    @GetMapping(value = "list")
    public ResponseEntity<List<OneSpu>> list(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                             @RequestParam(value = "spuName", required = false) String spuName) {
        try {
            OneSpu spu = new OneSpu();
            spu.setCategoryId(categoryId);
            spu.setSpuName(spuName);
            return ResponseEntity.ok(service.listForWechat(spu));
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
            return ResponseEntity.ok(service.getWithOther(id));
        } catch (Exception e) {
            log.error("get spu by id error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
