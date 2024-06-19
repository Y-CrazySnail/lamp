package com.yeem.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceMonthDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServiceMonthMapper extends BaseMapper<ServiceMonthDo> {

}
