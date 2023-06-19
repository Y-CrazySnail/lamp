package com.snail.king.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.king.entity.KingModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("king")
public interface KingModelMapper extends BaseMapper<KingModel> {

}
