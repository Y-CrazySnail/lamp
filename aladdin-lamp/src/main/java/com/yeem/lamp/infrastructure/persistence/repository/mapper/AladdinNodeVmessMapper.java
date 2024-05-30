package com.yeem.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinNodeVmess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("proxy")
public interface AladdinNodeVmessMapper extends BaseMapper<AladdinNodeVmess> {
    boolean updateByValidServiceIdList(@Param(value = "serviceIdList") List<Long> serviceIdList);
}
