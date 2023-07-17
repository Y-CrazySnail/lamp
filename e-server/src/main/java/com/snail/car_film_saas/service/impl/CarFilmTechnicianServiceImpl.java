package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarFilmTechnician;
import com.snail.car_film_saas.mapper.CarFilmTechnicianMapper;
import com.snail.car_film_saas.service.ICarFilmTechnicianService;
import org.springframework.stereotype.Service;

@Service
public class CarFilmTechnicianServiceImpl extends ServiceImpl<CarFilmTechnicianMapper, CarFilmTechnician> implements ICarFilmTechnicianService {
}
