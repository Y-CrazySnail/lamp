package com.snail.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.demo.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
