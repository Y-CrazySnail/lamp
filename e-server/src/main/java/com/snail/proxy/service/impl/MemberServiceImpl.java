package com.snail.proxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.proxy.entity.Member;
import com.snail.proxy.mapper.MemberMapper;
import com.snail.proxy.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void resetMemberData() {
        memberMapper.resetMemberData();
    }

    @Override
    public void calculateData() {
        memberMapper.calculateData();
    }
}
