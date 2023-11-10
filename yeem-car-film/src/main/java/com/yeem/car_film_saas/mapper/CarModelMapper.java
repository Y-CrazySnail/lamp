package com.yeem.car_film_saas.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.car_film_saas.entity.BaseCarModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("car-film-saas")
public interface CarModelMapper extends BaseMapper<BaseCarModel> {
}
