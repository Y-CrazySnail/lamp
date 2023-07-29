package com.yeem.proxy.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeem.proxy.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("proxy")
public interface MemberMapper extends BaseMapper<Member> {
    boolean resetMemberData();

    boolean calculateData();
}
