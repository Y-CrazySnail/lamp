package com.snail.car_film_saas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.car_film_saas.entity.CarFilmMessage;
import com.snail.car_film_saas.entity.CarFilmPrice;
import com.snail.car_film_saas.entity.CarFilmProduct;

import java.util.List;

public interface ICarFilmPriceService extends IService<CarFilmPrice> {
    List<CarFilmPrice> list(CarFilmPrice carFilmPrice);

    IPage<CarFilmPrice> pages(int current, int size, CarFilmPrice carFilmPrice);
    CarFilmPrice getById(Long id);

    void remove(CarFilmPrice carFilmPrice);


    boolean save(CarFilmPrice carFilmPrice);


    void update(CarFilmPrice carFilmPrice);
}
