package com.snail.chinaybop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.chinaybop.entity.ChinaybopQuality;
import com.snail.chinaybop.service.IChinaybopQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chinaybop-quality")
public class ChinaybopQualityController extends BaseController<ChinaybopQuality> {

    @Autowired
    private IChinaybopQualityService qualityService;

    @PostMapping("getByCondition")
    public ResponseEntity<Object> getByCondition(@RequestBody ChinaybopQuality quality) {
        QueryWrapper<ChinaybopQuality> qualityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(quality.getPhone())) {
            qualityQueryWrapper.eq("phone", quality.getPhone());
        } else if (!StringUtils.isEmpty(quality.getCarShelfNumber())) {
            qualityQueryWrapper.eq("car_shelf_number", quality.getCarShelfNumber());
        } else if (!StringUtils.isEmpty(quality.getQualityCardId())) {
            qualityQueryWrapper.eq("quality_card_id", quality.getQualityCardId());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        quality = qualityService.getOne(qualityQueryWrapper);
        if (StringUtils.isEmpty(quality)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(quality);
    }
}
