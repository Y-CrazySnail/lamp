package com.snail.king.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.king.entity.KingQuality;
import com.snail.king.service.IKingQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/king-quality")
public class KingQualityController extends BaseController<KingQuality> {

    @Autowired
    private IKingQualityService kingQualityService;

    @GetMapping("get")
    public ResponseEntity<Object> get(KingQuality kingQuality) {
        QueryWrapper<KingQuality> kingQualityQueryWrapper = new QueryWrapper<>();
        kingQualityQueryWrapper.eq("phone", kingQuality.getPhone());
        if (!StringUtils.isEmpty(kingQuality.getCarShelfNumber())) {
            kingQualityQueryWrapper.eq("car_shelf_number", kingQuality.getCarShelfNumber());
        } else if (!StringUtils.isEmpty(kingQuality.getQualityCardId())) {
            kingQualityQueryWrapper.eq("quality_card_id", kingQuality.getQualityCardId());
        } else {
            kingQualityQueryWrapper.eq("car_number", kingQuality.getCarNumber());
        }
        List<KingQuality> kingQualityList = kingQualityService.list(kingQualityQueryWrapper);
        return ResponseEntity.ok(kingQualityList);
    }
}
