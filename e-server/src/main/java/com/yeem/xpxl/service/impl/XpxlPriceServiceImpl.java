package com.yeem.xpxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.xpxl.entity.XpxlPrice;
import com.yeem.xpxl.mapper.XpxlPriceMapper;
import com.yeem.xpxl.service.IXpxlPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XpxlPriceServiceImpl extends ServiceImpl<XpxlPriceMapper, XpxlPrice> implements IXpxlPriceService {

    @Autowired
    private XpxlPriceMapper priceMapper;

    @Override
    public XpxlPrice getByEntity(XpxlPrice price) {
        QueryWrapper<XpxlPrice> priceQueryWrapper = new QueryWrapper<>();
        priceQueryWrapper.eq("level", price.getLevel());
        return priceMapper.selectOne(priceQueryWrapper);
    }
}
