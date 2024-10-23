package com.yeem.car.service.manage;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BCDictionary;
import com.yeem.car.mapper.BCDictionaryMapper;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageBCDictionaryService extends ServiceImpl<BCDictionaryMapper, BCDictionary> {

    @Autowired
    private BCDictionaryMapper dictionaryMapper;

    public List<BCDictionary> list(String dictType) {
        return list(dictType, null);
    }

    public List<BCDictionary> list(String dictType, String tenantNo) {
        return list(dictType, tenantNo, null);
    }

    public List<BCDictionary> list(String dictType, String tenantNo, String productNo) {
        LambdaQueryWrapper<BCDictionary> queryWrapper = Wrappers.lambdaQuery(BCDictionary.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(BCDictionary::getDictType, dictType);
        if (StrUtil.isNotEmpty(tenantNo)) {
            queryWrapper.eq(BCDictionary::getTenantNo, tenantNo);
        }
        if (StrUtil.isNotEmpty(productNo)) {
            queryWrapper.eq(BCDictionary::getProductNo, productNo);
        }
        return dictionaryMapper.selectList(queryWrapper);
    }
}
