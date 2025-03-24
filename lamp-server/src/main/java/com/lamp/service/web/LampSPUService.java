package com.lamp.service.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampProduct;
import com.lamp.entity.LampSPU;
import com.lamp.mapper.LampSPUMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LampSPUService extends ServiceImpl<LampSPUMapper, LampSPU> {

    @Autowired
    private LampSPUMapper spuMapper;

    @Autowired
    private LampMemberService memberService;

    @Autowired
    private LampSKUService skuService;

    public List<LampSPU> list() {
        LambdaQueryWrapper<LampSPU> queryWrapper = new LambdaQueryWrapper<>(LampSPU.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return spuMapper.selectList(queryWrapper);
    }

    public List<LampSPU> listByMemberId(Long memberId) {
        List<LampSPU> spuList = list();
        LampMember member = memberService.getById(memberId);
        if (member.getExpiryDate().isBefore(LocalDate.now()) || member.getExpiryDate().isEqual(LocalDate.now())) {
            spuList = spuList.stream().filter(spu -> spu.getSpuType().equals("regular")).collect(Collectors.toList());
        }
        for (LampSPU spu : spuList) {
            skuService.setSKUList(spu);
        }
        return spuList;
    }
}
