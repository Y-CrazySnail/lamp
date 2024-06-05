package com.yeem.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.lamp.infrastructure.persistence.entity.OrderDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderDo> {
}
