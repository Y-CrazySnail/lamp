package com.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lamp.infrastructure.persistence.entity.ServiceRecordDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServiceRecordMapper extends BaseMapper<ServiceRecordDo> {

}
