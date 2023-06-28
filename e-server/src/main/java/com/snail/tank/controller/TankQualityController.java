package com.snail.tank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.tank.entity.TankQuality;
import com.snail.tank.service.ITankQualityService;
import com.snail.conreoller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tank-quality")
public class TankQualityController extends BaseController<TankQuality> {

    @Autowired
    private ITankQualityService qualityService;

    @PostMapping("getByCondition")
    public ResponseEntity<Object> getByCondition(@RequestBody TankQuality quality) {
        QueryWrapper<TankQuality> qualityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(quality.getPhone())) {
            qualityQueryWrapper.eq("phone", quality.getPhone());
        } else if (!StringUtils.isEmpty(quality.getCarShelfNumber())) {
            qualityQueryWrapper.eq("car_shelf_number", quality.getCarShelfNumber());
        } else if (!StringUtils.isEmpty(quality.getQualityCardId())) {
            qualityQueryWrapper.eq("quality_card_id", quality.getQualityCardId());
        } else if (!StringUtils.isEmpty(quality.getRollNumber())) {
            qualityQueryWrapper.eq("roll_number", quality.getRollNumber());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        List<TankQuality> qualityList = qualityService.list(qualityQueryWrapper);
        if (qualityList.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(qualityList);
    }

    @GetMapping("pageByCreateUser")
    public ResponseEntity<Object> getPageByCreateUser(Integer current,
                                                      Integer size) {
        QueryWrapper<TankQuality> tankQualityQueryWrapper = new QueryWrapper<>();
        tankQualityQueryWrapper.eq("create_user", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return super.getPage(current, size, tankQualityQueryWrapper);
    }
}
