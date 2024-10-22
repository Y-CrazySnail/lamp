package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFQuality;
import com.yeem.car.mapper.CFQualityMapper;
import com.yeem.car.service.ICFQualityService;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CFQualityServiceImpl extends ServiceImpl<CFQualityMapper, CFQuality> implements ICFQualityService {

    @Autowired
    private CFQualityMapper qualityMapper;

    @Override
    public List<CFQuality> listForWechat(String tenantNo, String queryKey) {
        LambdaQueryWrapper<CFQuality> queryWrapper = Wrappers.lambdaQuery(CFQuality.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(CFQuality::getTenantNo, tenantNo);
        queryWrapper.eq(CFQuality::getApproveFlag, "1");
        queryWrapper.and(wrapper ->
                wrapper.eq(CFQuality::getPhone, queryKey).or().eq(CFQuality::getVin, queryKey).or().eq(CFQuality::getQualityCardNo, queryKey)
        );
        return qualityMapper.selectList(queryWrapper);
    }
}
