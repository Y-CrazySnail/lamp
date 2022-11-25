package com.snail.aili.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.aili.entity.AiliQuality;
import com.snail.aili.service.IAiliQualityService;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aili-quality")
public class AiliQualityController extends BaseController<AiliQuality> {

    @Autowired
    private IAiliQualityService qualityService;

    @GetMapping("getPage")
    public ResponseEntity<Object> getPage(Integer current,
                                          Integer size,
                                          String name,
                                          String phone,
                                          String checkStatus
    ) {
        QueryWrapper<AiliQuality> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("check_status");
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(phone)) {
            queryWrapper.like("phone", phone);
        }
        if (!StringUtils.isEmpty(checkStatus)) {
            queryWrapper.eq("check_status", checkStatus);
        }
        return ResponseEntity.ok(super.getPage(current, size, queryWrapper));
    }

    @PostMapping("getByCondition")
    public ResponseEntity<Object> getByCondition(@RequestBody AiliQuality quality) {
        QueryWrapper<AiliQuality> qualityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(quality.getPhone())) {
            qualityQueryWrapper.eq("phone", quality.getPhone());
        } else if (!StringUtils.isEmpty(quality.getCarShelfNumber())) {
            qualityQueryWrapper.eq("car_shelf_number", quality.getCarShelfNumber());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        qualityQueryWrapper.eq("check_status", "1");
        quality = qualityService.getOne(qualityQueryWrapper);
        if (StringUtils.isEmpty(quality)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(quality);
    }
}
