package com.snail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.entity.Brand;
import com.snail.entity.Price;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

}
