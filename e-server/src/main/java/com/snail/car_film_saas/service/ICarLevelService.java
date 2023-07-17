package com.snail.car_film_saas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.car_film_saas.entity.CarLevel;

public interface ICarLevelService extends IService<CarLevel> {
    String queryCarLevel(String levelNo);

}
