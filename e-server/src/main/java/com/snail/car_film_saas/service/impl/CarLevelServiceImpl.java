package com.snail.car_film_saas.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.snail.car_film_saas.entity.CarLevel;

import com.snail.car_film_saas.mapper.CarLevelMapper;

import com.snail.car_film_saas.service.ICarLevelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarLevelServiceImpl extends ServiceImpl<CarLevelMapper, CarLevel implements ICarLevelService {
@Autowired
private CarLevelMapper carLevelMapper;


    @Override
    public String queryCarLevel(String levelNo) {
        QueryWrapper<CarLevel> carLevelQueryWrapper=new QueryWrapper<>();
        carLevelQueryWrapper.eq("level_no",levelNo);
      carLevelMapper.selectList(carLevelQueryWrapper);
    }
}
