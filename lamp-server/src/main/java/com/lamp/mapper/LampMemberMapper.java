package com.lamp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lamp.entity.LampMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LampMemberMapper extends BaseMapper<LampMember>{
    void syncBandwidth(@Param(value = "memberId") Long memberId);
}