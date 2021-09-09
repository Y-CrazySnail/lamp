package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.entity.Brand;
import com.snail.entity.Price;
import com.snail.mapper.BrandMapper;
import com.snail.mapper.PriceMapper;
import com.snail.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> list() {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("first_char");
        brandMapper.selectList(queryWrapper);
        return null;
    }
}
