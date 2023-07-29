package com.yeem.proxy.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.proxy.entity.Node;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("proxy")
public interface NodeMapper extends BaseMapper<Node> {
}
