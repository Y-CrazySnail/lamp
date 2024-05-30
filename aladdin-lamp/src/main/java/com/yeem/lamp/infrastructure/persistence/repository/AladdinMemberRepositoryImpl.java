package com.yeem.lamp.infrastructure.persistence.repository;

import com.yeem.lamp.domain.repository.AladdinMemberRepository;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.AladdinMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AladdinMemberRepositoryImpl implements AladdinMemberRepository {

    @Autowired
    private AladdinMemberMapper aladdinMemberMapper;
}
