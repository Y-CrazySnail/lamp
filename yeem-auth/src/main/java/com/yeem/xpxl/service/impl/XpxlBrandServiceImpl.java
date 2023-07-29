package com.yeem.xpxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.xpxl.entity.XpxlBrand;
import com.yeem.xpxl.mapper.XpxlBrandMapper;
import com.yeem.xpxl.service.IXpxlBrandService;
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
