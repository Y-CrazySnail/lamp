package com.yeem.car.controller.wechat;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.yeem.car.entity.CFQuality;
import com.yeem.car.security.WechatAuthInterceptor;
import com.yeem.car.service.wechat.WechatCFQualityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/wechat/quality")
public class CFQualityWechatController {

    @Autowired
    @Qualifier(value = "wechatCFQualityService")
    private WechatCFQualityService qualityService;

    /**
     * 查询质保信息
     *
     * @param tenantNo 客户代码
     * @param queryKey 查询关键字
     * @return 质保信息列表
     */
    @GetMapping("list")
    public ResponseEntity<Object> getQualityInfo(@RequestParam(value = "tenantNo") String tenantNo,
                                                 @RequestParam(value = "queryKey") String queryKey) {
        if (StrUtil.isEmpty(queryKey)) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询质保失败");
        }
        try {
            List<CFQuality> qualityList = qualityService.list(tenantNo, queryKey);
            return ResponseEntity.ok(qualityList);
        } catch (Exception e) {
            log.error("查询质保失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("查询质保失败");
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> saveQualityInfo(@RequestBody CFQuality quality) {
        Long userId = WechatAuthInterceptor.getUserId();
        if (null == userId) {
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("鉴权失败");
        }
        try {
            qualityService.save(quality);
            return ResponseEntity.ok("录入质保成功");
        } catch (Exception e) {
            log.error("录入质保失败", e);
            return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR).body("录入质保失败");
        }
    }

}
