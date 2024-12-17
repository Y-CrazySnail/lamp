package com.lamp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lamp.entity.LampService;
import com.lamp.entity.LampSubscription;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LampSubscriptionMapper extends BaseMapper<LampSubscription>{
}