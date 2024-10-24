package com.yeem.lamp.domain.service.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.objvalue.Token;
import com.yeem.lamp.infrastructure.persistence.repository.web.MemberWebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberWebDomainService {

    @Autowired
    private MemberWebRepository memberRepository;

    /**
     * 登录
     *
     * @param member 会员信息
     * @return 登录token
     */
    public Token login(Member member) {
        List<Member> memberList = memberRepository.list(member);
        if (memberList.isEmpty()) {
            return null;
        }
        Member resMember = memberList.get(0);
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
