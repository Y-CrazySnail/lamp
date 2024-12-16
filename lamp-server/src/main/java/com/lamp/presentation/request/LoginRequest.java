package com.lamp.presentation.request;

import com.lamp.domain.entity.Member;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    public Member convertMember() {
        Member member = new Member();
        member.setEmail(this.username);
        member.setPassword(this.password);
        return member;
    }
}
