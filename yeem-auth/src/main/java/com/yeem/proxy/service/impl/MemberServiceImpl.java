package com.yeem.proxy.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.proxy.entity.Member;
import com.yeem.proxy.mapper.MemberMapper;
import com.yeem.proxy.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DS("proxy")
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @DS("proxy")
    @Override
    public void resetMemberData() {
        memberMapper.resetMemberData();
    }

    @DS("proxy")
    @Override
    public void calculateData() {
        memberMapper.calculateData();
    }
}
