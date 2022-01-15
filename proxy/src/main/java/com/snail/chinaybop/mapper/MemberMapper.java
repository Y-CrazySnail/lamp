package com.snail.chinaybop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snail.chinaybop.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    boolean increaseMacNum(Long id);
    boolean increaseWindowsNum(Long id);
    boolean increaseAndroidNum(Long id);
    boolean increaseIphoneNum(Long id);
}
