package com.snail.tank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.tank.entity.TankBrand;
import com.snail.tank.mapper.TankBrandMapper;
import com.snail.tank.service.ITankBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TankBrandServiceImpl extends ServiceImpl<TankBrandMapper, TankBrand> implements ITankBrandService {

    @Autowired
    private TankBrandMapper brandMapper;

    @Override
    public List<TankBrand> list() {
        QueryWrapper<TankBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("first_char");
        return brandMapper.selectList(queryWrapper);
    }
}
