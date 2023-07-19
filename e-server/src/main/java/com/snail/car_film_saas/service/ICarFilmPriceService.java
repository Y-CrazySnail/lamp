package com.snail.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.car_film_saas.entity.CarFilmPrice;

import java.util.List;

public interface ICarFilmPriceService extends IService<CarFilmPrice> {
    List<CarFilmPrice> list(String productNo,String productLevelNo,String carLevelNo);

    IPage<CarFilmPrice> pages(int current, int size, String productNo,String productLevelNo,String carLevelNo);
    CarFilmPrice getById(Long id);

    void remove(CarFilmPrice carFilmPrice);


    boolean save(CarFilmPrice carFilmPrice);


    void update(CarFilmPrice carFilmPrice);
}
