package com.yeem.lamp.infrastructure.persistence.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.infrastructure.persistence.entity.MemberDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper extends BaseMapper<MemberDo> {
    IPage<MemberDo> pages(IPage<MemberDo> page,
                          @Param(value = "wechat") String wechat,
                          @Param(value = "email") String email);
}
