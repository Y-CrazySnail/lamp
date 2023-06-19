package com.snail.aili.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.aili.entity.AiliBrand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("aili")
public interface AiliBrandMapper extends BaseMapper<AiliBrand> {

}
