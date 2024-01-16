package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinPackage;
import com.yeem.lamp.entity.AladdinServer;
import com.yeem.lamp.mapper.AladdinPackageMapper;
import com.yeem.lamp.service.IAladdinPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AladdinPackageServiceImpl extends ServiceImpl<AladdinPackageMapper, AladdinPackage> implements IAladdinPackageService {

    @Autowired
    private AladdinPackageMapper aladdinPackageMapper;

    @Override
    public IPage<AladdinPackage> pages(int current, int size) {
        QueryWrapper<AladdinPackage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<AladdinPackage> page = new Page<>(current, size);
        return aladdinPackageMapper.selectPage(page, queryWrapper);
    }
}
