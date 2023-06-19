package com.snail.xpxl.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.xpxl.entity.XpxlPrice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("xpxl")
public interface XpxlPriceMapper extends BaseMapper<XpxlPrice> {

}
