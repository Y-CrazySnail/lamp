package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Member;

import java.util.List;

public interface MemberRepository {
    Member getById(Long id);
    List<Member> list(Member member);
    IPage<Member> pages(int current, int size, String email, String wechat);
    void save(Member member);
    void updateById(Member member);
    void removeById(Long id);
}
