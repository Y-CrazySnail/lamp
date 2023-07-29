package com.yeem.king.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.king.entity.KingBrand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("king")
public interface KingBrandMapper extends BaseMapper<KingBrand> {

}
