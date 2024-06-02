package com.yeem.lamp.domain.factory;

import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.presentation.request.LoginRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberFactory {
    public Member create(LoginRequest loginRequest) {
        Member member = new Member();
        member.setEmail(loginRequest.getUsername());
        member.setPassword(loginRequest.getPassword());
        return member;
    }
}
