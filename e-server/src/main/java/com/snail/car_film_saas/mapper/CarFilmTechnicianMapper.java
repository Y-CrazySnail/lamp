package com.snail.car_film_saas.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.car_film_saas.entity.CarFilmTechnician;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("car-film-saas")
public interface CarFilmTechnicianMapper extends BaseMapper<CarFilmTechnician> {
}
