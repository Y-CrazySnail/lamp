package com.yeem.lamp.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.lamp.entity.Server;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("proxy")
public interface ServerMapper extends BaseMapper<Server> {

}
