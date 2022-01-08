package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.entity.XpxlQuality;
import com.snail.mapper.XpxlQualityMapper;
import com.snail.service.IXpxlQualityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quality")
@Api(value = "质保接口", tags = "质保接口")
public class XpxlQualityController extends BaseController<XpxlQuality> {

    @Autowired
    private IXpxlQualityService qualityService;

    @Autowired
    private XpxlQualityMapper xpxlQualityMapper;

    @PostMapping("getByCondition")
    public ResponseEntity<Object> getByCondition(@RequestBody XpxlQuality quality) {
        QueryWrapper<XpxlQuality> qualityQueryWrapper = new QueryWrapper<>();
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
