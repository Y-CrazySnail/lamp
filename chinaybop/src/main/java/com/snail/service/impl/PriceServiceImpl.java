package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.Price;
import com.snail.mapper.PriceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.service.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl extends ServiceImpl<PriceMapper, Price> implements IPriceService {

    @Autowired
    private PriceMapper priceMapper;

    @Override
    public Price getByEntity(Price price) {
        QueryWrapper<Price> priceQueryWrapper = new QueryWrapper<>();
        priceQueryWrapper.eq("type", price.getType());
        priceQueryWrapper.eq("level", price.getLevel());
        return priceMapper.selectOne(priceQueryWrapper);
    }
}
