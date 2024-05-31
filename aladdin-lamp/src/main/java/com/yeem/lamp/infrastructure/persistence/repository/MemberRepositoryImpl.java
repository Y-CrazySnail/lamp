package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.repository.MemberRepository;
import com.yeem.lamp.infrastructure.persistence.entity.MemberEntity;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member get(Member member) {
        LambdaQueryWrapper<MemberEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberEntity::getEmail, member.getEmail());
        queryWrapper.eq(MemberEntity::getDeleteFlag, false);
        MemberEntity memberEntity = memberMapper.selectOne(queryWrapper);
        member.setId(memberEntity.getId());
        member.setEmail(memberEntity.getEmail());
        member.setWechat(memberEntity.getWechat());
        member.setRemark(memberEntity.getRemark());
        member.setPassword(memberEntity.getPassword());
        member.setLastUpdateSubscription(memberEntity.getLastUpdateSubscription());
        return member;
    }
}
