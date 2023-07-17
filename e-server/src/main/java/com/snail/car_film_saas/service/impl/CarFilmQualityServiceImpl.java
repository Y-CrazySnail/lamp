package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarFilmQuality;
import com.snail.car_film_saas.mapper.CarFilmQualityMapper;
import com.snail.car_film_saas.service.ICarFilmQualityService;
import org.springframework.stereotype.Service;

@Service
public class CarFilmQualityServiceImpl extends ServiceImpl<CarFilmQualityMapper, CarFilmQuality>implements ICarFilmQualityService {
}
