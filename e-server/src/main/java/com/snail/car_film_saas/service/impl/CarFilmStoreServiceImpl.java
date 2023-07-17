package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarFilmStore;
import com.snail.car_film_saas.mapper.CarFilmStoreMapper;
import com.snail.car_film_saas.service.ICarFilmStoreService;
import org.springframework.stereotype.Service;

@Service
public class CarFilmStoreServiceImpl extends ServiceImpl<CarFilmStoreMapper, CarFilmStore> implements ICarFilmStoreService {
}
