package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BCBrand;
import com.yeem.car.mapper.BCBrandMapper;
import com.yeem.car.service.IBCBrandService;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BCBrandServiceImpl extends ServiceImpl<BCBrandMapper, BCBrand> implements IBCBrandService {

    @Autowired
    private BCBrandMapper brandMapper;

    @Override
    public List<BCBrand> listForWechat() {
        LambdaQueryWrapper<BCBrand> queryWrapper = Wrappers.lambdaQuery(BCBrand.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.orderByAsc(BCBrand::getNameEn);
        return brandMapper.selectList(queryWrapper);
    }
}
