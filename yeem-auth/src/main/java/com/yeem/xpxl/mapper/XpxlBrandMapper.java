package com.yeem.xpxl.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.xpxl.entity.XpxlBrand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("xpxl")
public interface XpxlBrandMapper extends BaseMapper<XpxlBrand> {

}
