package com.snail.car_film_saas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.car_film_saas.entity.CarFilmMessage;
import com.snail.car_film_saas.mapper.CarFilmMessageMapper;
import com.snail.car_film_saas.service.ICarFilmMessageService;
import org.springframework.stereotype.Service;

@Service
public class CarFilmMessageServiceImpl extends ServiceImpl<CarFilmMessageMapper, CarFilmMessage> implements ICarFilmMessageService {

}
