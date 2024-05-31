package com.yeem.lamp.domain.service;

import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.objvalue.Token;
import com.yeem.lamp.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberDomainService {

    @Autowired
    private MemberRepository memberRepository;

    public Token login(Member member) {
        Member resMember = memberRepository.get(member);
        if (!member.getPassword().equals(resMember.getPassword())) {
            return null;
        }
        Token token = new Token();
        token.setToken(resMember.getId());
        return token;
    }
}
