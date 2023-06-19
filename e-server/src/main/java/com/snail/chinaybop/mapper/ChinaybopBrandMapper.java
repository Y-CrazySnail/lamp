package com.snail.chinaybop.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.chinaybop.entity.ChinaybopBrand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("chinaybop")
public interface ChinaybopBrandMapper extends BaseMapper<ChinaybopBrand> {

}
