package com.yeem.lamp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yeem.lamp.entity.Member;
import com.yeem.lamp.mapper.MemberMapper;
import com.yeem.lamp.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DS("proxy")
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}
