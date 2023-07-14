package com.snail.zero.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.zero.entity.ZeroOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("zero")
public interface ZeroOrderMapper extends BaseMapper<ZeroOrder> {
}
