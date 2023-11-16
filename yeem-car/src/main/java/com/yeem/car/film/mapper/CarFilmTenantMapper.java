package com.yeem.car.film.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.car.film.entity.CarFilmTenant;
import org.apache.ibatis.annotations.Mapper;


@Mapper
@DS("car-film-saas")
public interface CarFilmTenantMapper extends BaseMapper<CarFilmTenant> {
}
