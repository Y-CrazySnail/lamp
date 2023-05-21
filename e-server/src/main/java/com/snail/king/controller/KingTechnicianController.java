package com.snail.king.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.king.entity.KingStore;
import com.snail.king.entity.KingTechnician;
import com.snail.king.service.IKingStoreService;
import com.snail.king.service.IKingTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        kingTechnicianQueryWrapper.eq("province", kingTechnician.getProvince());
        kingTechnicianQueryWrapper.eq("city", kingTechnician.getCity());
        kingTechnicianQueryWrapper.eq("county", kingTechnician.getCounty());
        List<KingTechnician> kingTechnicianList = technicianService.list(kingTechnicianQueryWrapper);
        return ResponseEntity.ok(kingTechnicianList);
    }
}
