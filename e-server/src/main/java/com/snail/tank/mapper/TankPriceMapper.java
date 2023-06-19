package com.snail.tank.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.tank.entity.TankPrice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("tank")
public interface TankPriceMapper extends BaseMapper<TankPrice> {

}
