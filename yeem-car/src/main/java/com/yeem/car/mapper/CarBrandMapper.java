package com.yeem.car.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.car.entity.BaseCarBrand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarBrandMapper extends BaseMapper<BaseCarBrand> {
}
