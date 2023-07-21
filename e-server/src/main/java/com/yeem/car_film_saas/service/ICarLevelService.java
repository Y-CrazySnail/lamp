package com.yeem.car_film_saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.car_film_saas.entity.CarLevel;

public interface ICarLevelService extends IService<CarLevel> {
    /**
     * 传进levelNo 查询  levelName
     * @param levelNo
     * @return
     */
    CarLevel queryCarLevel(String levelNo);

}
