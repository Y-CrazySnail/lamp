package com.yeem.lamp.domain.repository;

import com.yeem.lamp.domain.entity.Member;

public interface MemberRepository {
    Member get(Member member);
    Member getById(Long id);
}
