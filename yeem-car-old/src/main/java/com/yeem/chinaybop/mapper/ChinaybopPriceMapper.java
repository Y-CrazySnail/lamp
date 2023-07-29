package com.yeem.chinaybop.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yeem.chinaybop.entity.ChinaybopPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("chinaybop")
public interface ChinaybopPriceMapper extends BaseMapper<ChinaybopPrice> {

}
