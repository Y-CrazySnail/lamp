package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarFilmPrice;
import com.snail.car_film_saas.mapper.CarFilmPriceMapper;
import com.snail.car_film_saas.service.ICarFilmPriceService;
import org.springframework.stereotype.Service;

@Service
public class CarFilmPriceServiceImpl extends ServiceImpl<CarFilmPriceMapper, CarFilmPrice> implements ICarFilmPriceService {
}
