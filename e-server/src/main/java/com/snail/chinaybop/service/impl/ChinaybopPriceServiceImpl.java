package com.snail.chinaybop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.chinaybop.entity.ChinaybopPrice;
import com.snail.chinaybop.mapper.ChinaybopPriceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.chinaybop.service.IChinaybopPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChinaybopPriceServiceImpl extends ServiceImpl<ChinaybopPriceMapper, ChinaybopPrice> implements IChinaybopPriceService {

    @Autowired
    private ChinaybopPriceMapper priceMapper;

    @Override
    public ChinaybopPrice getByEntity(ChinaybopPrice price) {
        QueryWrapper<ChinaybopPrice> priceQueryWrapper = new QueryWrapper<>();
        priceQueryWrapper.eq("type", price.getType());
        priceQueryWrapper.eq("level", price.getLevel());
        return priceMapper.selectOne(priceQueryWrapper);
    }
}
