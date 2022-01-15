package com.snail.chinaybop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.chinaybop.entity.Member;
import com.snail.chinaybop.mapper.MemberMapper;
import com.snail.chinaybop.service.IMemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}
