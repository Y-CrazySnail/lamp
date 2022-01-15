package com.snail.xpxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.xpxl.entity.XpxlBrand;
import com.snail.xpxl.mapper.XpxlBrandMapper;
import com.snail.xpxl.service.IXpxlBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XpxlBrandServiceImpl extends ServiceImpl<XpxlBrandMapper, XpxlBrand> implements IXpxlBrandService {

    @Autowired
    private XpxlBrandMapper brandMapper;

    @Override
    public List<XpxlBrand> list() {
        QueryWrapper<XpxlBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("first_char");
        return brandMapper.selectList(queryWrapper);
    }
}
