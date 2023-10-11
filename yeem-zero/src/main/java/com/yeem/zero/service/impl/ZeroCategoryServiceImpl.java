package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.zero.entity.ZeroCategory;
import com.yeem.zero.entity.ZeroProduct;
import com.yeem.zero.mapper.ZeroCategoryMapper;
import com.yeem.zero.service.IZeroCategoryService;
import com.yeem.zero.service.IZeroProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ZeroCategoryServiceImpl extends ServiceImpl<ZeroCategoryMapper, ZeroCategory> implements IZeroCategoryService {

    @Autowired
    private ZeroCategoryMapper zeroCategoryMapper;

    @Autowired
    private IZeroProductService zeroProductService;

    @Override
    public List<ZeroCategory> list() {
        QueryWrapper<ZeroCategory> zeroCategoryQueryWrapper = new QueryWrapper<>();
        zeroCategoryQueryWrapper.orderByAsc("sort");
        zeroCategoryQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        List<ZeroCategory> zeroCategoryList = zeroCategoryMapper.selectList(zeroCategoryQueryWrapper);
        if (!zeroCategoryList.isEmpty()) {
            zeroCategoryList.forEach(zeroCategory -> {
                List<ZeroProduct> zeroProductList = zeroProductService.listByCategoryId(zeroCategory.getId());
                zeroCategory.setZeroProductList(zeroProductList);
            });
        }
        return zeroCategoryList;
    }

    @Override
    public List<ZeroCategory> dict() {
        QueryWrapper<ZeroCategory> zeroCategoryQueryWrapper = new QueryWrapper<>();
        zeroCategoryQueryWrapper.orderByAsc("sort");
        zeroCategoryQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        return zeroCategoryMapper.selectList(zeroCategoryQueryWrapper);
    }
}
