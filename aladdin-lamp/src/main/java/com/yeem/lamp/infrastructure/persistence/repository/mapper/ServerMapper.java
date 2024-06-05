package com.yeem.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServerMapper extends BaseMapper<ServerDo> {
}
