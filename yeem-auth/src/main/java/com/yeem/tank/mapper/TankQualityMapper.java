package com.yeem.tank.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.tank.entity.TankQuality;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("tank")
public interface TankQualityMapper extends BaseMapper<TankQuality> {

}
