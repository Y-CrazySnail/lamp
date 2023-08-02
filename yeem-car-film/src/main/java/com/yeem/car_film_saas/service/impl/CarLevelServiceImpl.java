package com.yeem.car_film_saas.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car_film_saas.entity.CarLevel;

import com.yeem.car_film_saas.mapper.CarLevelMapper;

import com.yeem.car_film_saas.service.ICarLevelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarLevelServiceImpl extends ServiceImpl<CarLevelMapper, CarLevel> implements ICarLevelService {
@Autowired
private CarLevelMapper carLevelMapper;

    /**
     * 按levelNo查询levelName
     *

     * @return
     */
    @Override
    public List<CarLevel> list() {
        return carLevelMapper.selectList(null);
    }
}
