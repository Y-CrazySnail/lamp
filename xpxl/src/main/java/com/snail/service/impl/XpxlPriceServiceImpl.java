package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.XpxlPrice;
import com.snail.mapper.XpxlPriceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.service.IXpxlPriceService;
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
