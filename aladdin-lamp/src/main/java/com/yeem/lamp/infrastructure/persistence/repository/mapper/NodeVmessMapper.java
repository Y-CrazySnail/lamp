package com.yeem.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("proxy")
public interface NodeVmessMapper extends BaseMapper<NodeVmessDo> {
    boolean updateByValidServiceIdList(@Param(value = "serviceIdList") List<Long> serviceIdList);
}
