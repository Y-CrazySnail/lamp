package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.repository.MemberRepository;
import com.yeem.lamp.infrastructure.persistence.entity.MemberDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member get(Member member) {
        LambdaQueryWrapper<MemberDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberDo::getEmail, member.getEmail());
        queryWrapper.eq(MemberDo::getDeleteFlag, false);
        MemberDo memberDo = memberMapper.selectOne(queryWrapper);
        return memberDo.convertMember();
    }

    @Override
    public Member getById(Long id) {
        MemberDo memberDo = memberMapper.selectById(id);
        return memberDo.convertMember();
    }
}
