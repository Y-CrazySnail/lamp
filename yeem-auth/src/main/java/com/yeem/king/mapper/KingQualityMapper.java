package com.yeem.king.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.king.entity.KingQuality;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("king")
public interface KingQualityMapper extends BaseMapper<KingQuality> {

}
