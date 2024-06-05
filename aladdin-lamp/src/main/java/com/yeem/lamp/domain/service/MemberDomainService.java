package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.application.dto.MemberDTO;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.objvalue.Token;
import com.yeem.lamp.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Member> list() {
        return memberRepository.list();
    }

    public IPage<Member> pages(int current, int size, String email, String wechat) {
        return memberRepository.pages(current, size, email, wechat);
    }

    public void updateById(Member member) {
        memberRepository.updateById(member);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void removeById(Long id) {
        memberRepository.removeById(id);
    }
}
