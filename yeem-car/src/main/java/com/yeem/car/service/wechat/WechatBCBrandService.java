package com.yeem.car.service.wechat;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.BCBrand;
import com.yeem.car.mapper.BCBrandMapper;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WechatBCBrandService extends ServiceImpl<BCBrandMapper, BCBrand> {

    @Autowired
    private BCBrandMapper brandMapper;

    public List<BCBrand> list() {
        LambdaQueryWrapper<BCBrand> queryWrapper = Wrappers.lambdaQuery(BCBrand.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.orderByAsc(BCBrand::getNameEn);
        return brandMapper.selectList(queryWrapper);
    }

}
