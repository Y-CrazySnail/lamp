package com.yeem.car.film.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.car.film.entity.CarFilmStore;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
@DS("car-film-saas")
public interface CarFilmStoreMapper extends BaseMapper<CarFilmStore> {

    List<CarFilmStore> selectAddress(Map<String,Object> map);

    List<BigDecimal> getDistance(Map<String,Object> map);
}
