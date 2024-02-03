package com.yeem.car.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.car.entity.BaseCarDictionary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("car-film-saas")
public interface CarDictionaryMapper extends BaseMapper<BaseCarDictionary> {
}
