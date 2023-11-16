package com.yeem.car.film.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.film.mapper.CarLevelMapper;
import com.yeem.car.film.service.ICarLevelService;
import com.yeem.car.film.entity.BaseCarLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarLevelServiceImpl extends ServiceImpl<CarLevelMapper, BaseCarLevel> implements ICarLevelService {
@Autowired
private CarLevelMapper carLevelMapper;

    /**
     * 按levelNo查询levelName
     *

     * @return
     */
    @Override
    public List<BaseCarLevel> list() {
        return carLevelMapper.selectList(null);
    }
}
