package com.snail.proxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.proxy.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    boolean resetMemberData();

    boolean calculateData();
}
