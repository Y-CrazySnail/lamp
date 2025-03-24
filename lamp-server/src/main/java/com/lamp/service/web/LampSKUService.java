package com.lamp.service.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampSKU;
import com.lamp.entity.LampSPU;
import com.lamp.mapper.LampSKUMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LampSKUService extends ServiceImpl<LampSKUMapper, LampSKU> {

    @Autowired
    private LampSKUMapper skuMapper;

    public void setSKUList(LampSPU spu) {
        LambdaQueryWrapper<LampSKU> queryWrapper = new LambdaQueryWrapper<>(LampSKU.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(LampSKU::getSpuId, spu.getId());
        List<LampSKU> skuList = skuMapper.selectList(queryWrapper);
        skuList.forEach(LampSKU::parseAttribute);
        spu.setSkuList(skuList);
    }
}
