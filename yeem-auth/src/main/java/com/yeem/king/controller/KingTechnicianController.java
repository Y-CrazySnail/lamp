package com.yeem.king.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.common.conreoller.BaseController;
import com.yeem.king.entity.KingTechnician;
import com.yeem.king.service.IKingTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/king-technician")
public class KingTechnicianController extends BaseController<KingTechnician> {
    @Autowired
    private IKingTechnicianService technicianService;

    @GetMapping("get")
    public ResponseEntity<Object> get(KingTechnician kingTechnician) {
        QueryWrapper<KingTechnician> kingTechnicianQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(kingTechnician.getProvince())) {
            kingTechnicianQueryWrapper.eq("province", kingTechnician.getProvince());
        }
        if (!StringUtils.isEmpty(kingTechnician.getCity())) {
            kingTechnicianQueryWrapper.eq("city", kingTechnician.getCity());
        }
        if (!StringUtils.isEmpty(kingTechnician.getCounty())) {
            kingTechnicianQueryWrapper.eq("county", kingTechnician.getCounty());
        }
        List<KingTechnician> kingTechnicianList = technicianService.list(kingTechnicianQueryWrapper);
        return ResponseEntity.ok(kingTechnicianList);
    }
}
