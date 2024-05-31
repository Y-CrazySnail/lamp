package com.yeem.lamp.domain.factory;

import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.presentation.request.MemberVO;
import org.springframework.stereotype.Component;

@Component
public class MemberFactory {
    public Member create(MemberVO memberVO) {
        Member member = new Member();
        member.setEmail(memberVO.getUsername());
        member.setPassword(memberVO.getPassword());
        return member;
    }
}
