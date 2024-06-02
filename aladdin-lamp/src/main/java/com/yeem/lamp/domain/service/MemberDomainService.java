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

    /**
     * 登录
     *
     * @param member 会员信息
     * @return 登录token
     */
    public Token login(Member member) {
        Member resMember = memberRepository.get(member);
        if (!member.getPassword().equals(resMember.getPassword())) {
            return null;
        }
        Token token = new Token();
        token.setToken(resMember.getId());
        return token;
    }

    public Member getById(Long id) {
        Member member = memberRepository.getById(id);
        member.setPassword(null);
        return member;
    }
}
