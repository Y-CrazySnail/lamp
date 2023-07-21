package com.yeem.proxy.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.proxy.entity.CommandConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("proxy")
public interface CommandConfigMapper extends BaseMapper<CommandConfig> {
}
