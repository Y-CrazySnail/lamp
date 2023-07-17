package com.snail.car_film_saas.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.car_film_saas.entity.CarFilmMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("car-film-saas")
public interface CarFilmMessageMapper extends BaseMapper<CarFilmMessage> {
}
