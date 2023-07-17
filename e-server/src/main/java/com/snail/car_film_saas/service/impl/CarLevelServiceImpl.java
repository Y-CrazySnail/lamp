package com.snail.car_film_saas.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarLevel;

import com.snail.car_film_saas.mapper.CarLevelMapper;

import com.snail.car_film_saas.service.ICarLevelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarLevelServiceImpl extends ServiceImpl<CarLevelMapper, CarLevel> implements ICarLevelService {
@Autowired
private CarLevelMapper carLevelMapper;

    /**
     * 按levelNo查询levelName
     * @param levelNo
     * @return
     */
    @Override
    public CarLevel queryCarLevel(String levelNo) {
        QueryWrapper<CarLevel> carLevelQueryWrapper=new QueryWrapper<>();
        carLevelQueryWrapper.eq("level_no",levelNo);
     return carLevelMapper.selectOne(carLevelQueryWrapper);

    }
}
