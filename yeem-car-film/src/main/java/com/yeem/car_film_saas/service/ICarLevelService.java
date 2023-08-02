package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarLevel;

import java.util.List;

public interface ICarLevelService extends IService<CarLevel> {
    /**
     * 传进levelNo 查询  levelName
     *
     * @return
     */
    List<CarLevel> list();

}
