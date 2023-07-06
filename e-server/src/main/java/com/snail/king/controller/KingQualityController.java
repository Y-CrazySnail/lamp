package com.snail.king.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.chinaybop.entity.ChinaybopQuality;
import com.snail.conreoller.BaseController;
import com.snail.king.entity.KingQuality;
import com.snail.king.service.IKingQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (!StringUtils.isEmpty(kingQuality.getPhone())) {
            kingQualityQueryWrapper.eq("phone", kingQuality.getPhone());
        } else if (!StringUtils.isEmpty(kingQuality.getCarShelfNumber())) {
            kingQualityQueryWrapper.eq("car_shelf_number", kingQuality.getCarShelfNumber());
        } else if (!StringUtils.isEmpty(kingQuality.getQualityCardId())) {
            kingQualityQueryWrapper.eq("quality_card_id", kingQuality.getQualityCardId());
        } else {
            kingQualityQueryWrapper.eq("car_number", kingQuality.getCarNumber());
        }
        List<KingQuality> kingQualityList = kingQualityService.list(kingQualityQueryWrapper);
        return ResponseEntity.ok(kingQualityList);
    }

    @GetMapping("quality-page")
    public ResponseEntity<Object> qualityPage(Integer current, Integer size) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        QueryWrapper<KingQuality> kingQualityQueryWrapper = new QueryWrapper<>();
        if (!"king".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())) {
            kingQualityQueryWrapper.eq("create_user", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        }
        return super.getPage(current, size, kingQualityQueryWrapper);
    }
}
