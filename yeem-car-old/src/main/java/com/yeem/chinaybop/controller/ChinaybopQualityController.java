package com.yeem.chinaybop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.common.conreoller.BaseController;
import com.yeem.chinaybop.entity.ChinaybopQuality;
import com.yeem.chinaybop.service.IChinaybopQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        } else if (!StringUtils.isEmpty(quality.getRollNumber())) {
            qualityQueryWrapper.eq("roll_number", quality.getRollNumber());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        List<ChinaybopQuality> qualityList = qualityService.list(qualityQueryWrapper);
        if (qualityList.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(qualityList);
    }

    @GetMapping("quality-page")
    public ResponseEntity<Object> qualityPage(Integer current, Integer size) {
        if (StringUtils.isEmpty(current)) {
            current = 1;
        }
        if (StringUtils.isEmpty(size)) {
            size = 10;
        }
        QueryWrapper<ChinaybopQuality> chinaybopQualityQueryWrapper = new QueryWrapper<>();
        if (!"chinaybop".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())) {
            chinaybopQualityQueryWrapper.eq("create_user", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        }
        return super.getPage(current, size, chinaybopQualityQueryWrapper);
    }
}
