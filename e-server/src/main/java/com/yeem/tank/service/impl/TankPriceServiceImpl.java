package com.yeem.tank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.tank.entity.TankPrice;
import com.yeem.tank.mapper.TankPriceMapper;
import com.yeem.tank.service.ITankPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TankPriceServiceImpl extends ServiceImpl<TankPriceMapper, TankPrice> implements ITankPriceService {

    @Autowired
    private TankPriceMapper priceMapper;

    @Override
    public TankPrice getByEntity(TankPrice price) {
        QueryWrapper<TankPrice> priceQueryWrapper = new QueryWrapper<>();
        priceQueryWrapper.eq("level", price.getLevel());
        return priceMapper.selectOne(priceQueryWrapper);
    }
}
