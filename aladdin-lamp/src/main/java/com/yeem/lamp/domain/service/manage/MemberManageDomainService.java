package com.yeem.lamp.domain.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.infrastructure.persistence.repository.manage.MemberManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberManageDomainService {

    @Autowired
    private MemberManageRepository memberRepository;

    public Member getById(Long id) {
        Member member = memberRepository.getById(id);
        member.setPassword(null);
        return member;
    }

    public List<Member> list(Member member) {
        return memberRepository.list(member);
    }

    public IPage<Member> pages(int current, int size, String email, String wechat) {
        return memberRepository.pages(current, size, email, wechat);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void updateById(Member member) {
        memberRepository.updateById(member);
    }

    public void removeById(Long id) {
        memberRepository.removeById(id);
    }
}
