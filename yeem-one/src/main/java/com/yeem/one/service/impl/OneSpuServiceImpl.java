package com.yeem.one.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneEvaluation;
import com.yeem.one.entity.OneSku;
import com.yeem.one.entity.OneSpu;
import com.yeem.one.mapper.OneSpuMapper;
import com.yeem.one.service.IOneEvaluationService;
import com.yeem.one.service.IOneSkuService;
import com.yeem.one.service.IOneSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OneSpuServiceImpl extends ServiceImpl<OneSpuMapper, OneSpu> implements IOneSpuService {

    @Autowired
    private OneSpuMapper mapper;
    @Autowired
    private IOneSkuService skuService;
    @Autowired
    private IOneEvaluationService evaluationService;

    @Override
    public List<OneSpu> list(OneSpu spu) {
        LambdaQueryWrapper<OneSpu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneSpu::getDeleteFlag, false);
        queryWrapper.eq(OneSpu::getTenantId, spu.getTenantId());
        queryWrapper.eq(OneSpu::getSpuStatus, true);
        if (null != spu.getCategoryId()) {
            queryWrapper.eq(OneSpu::getCategoryId, spu.getCategoryId());
        } else {
            if (StrUtil.isEmpty(spu.getSpuName())) {
                return new ArrayList<>();
            }
        }
        if (StrUtil.isNotEmpty(spu.getSpuName())) {
            queryWrapper.like(OneSpu::getSpuName, spu.getSpuName());
        }
        List<OneSpu> spuList = mapper.selectList(queryWrapper);
        for (OneSpu item : spuList) {
            List<OneSku> skuList = skuService.listBySpuId(item.getId());
            item.setSkuList(skuList);
        }
        spuList.removeIf(oneSpu -> oneSpu.getSkuList().isEmpty());
        return spuList;
    }

    @Override
    public OneSpu getWithOther(Long id, Long tenantId) {
        LambdaQueryWrapper<OneSpu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneSpu::getDeleteFlag, false);
        queryWrapper.eq(OneSpu::getTenantId, tenantId);
        queryWrapper.eq(OneSpu::getId, id);
        OneSpu spu = mapper.selectOne(queryWrapper);
        List<OneSku> skuList = skuService.listBySpuId(id);
        spu.setSkuList(skuList);
        List<OneEvaluation> evaluationList = evaluationService.listBySpuId(id);
        spu.setEvaluationList(evaluationList);
        return spu;
    }
}
