package com.yeem.car.service.wechat;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFQuality;
import com.yeem.car.mapper.CFQualityMapper;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WechatCFQualityService extends ServiceImpl<CFQualityMapper, CFQuality> {

    @Autowired
    private CFQualityMapper qualityMapper;

    public List<CFQuality> list(String tenantNo, String queryKey) {
        LambdaQueryWrapper<CFQuality> queryWrapper = Wrappers.lambdaQuery(CFQuality.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(CFQuality::getTenantNo, tenantNo);
        queryWrapper.eq(CFQuality::getApproveFlag, "1");
        queryWrapper.and(wrapper ->
                wrapper.eq(CFQuality::getPhone, queryKey).or().eq(CFQuality::getVin, queryKey).or().eq(CFQuality::getQualityCardNo, queryKey)
        );
        List<CFQuality> qualityList = qualityMapper.selectList(queryWrapper);
        for (CFQuality quality : qualityList) {
            quality.setState();
        }
        return qualityList;
    }
}
