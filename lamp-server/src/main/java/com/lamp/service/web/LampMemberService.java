package com.lamp.service.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.mapper.LampMemberMapper;
import com.lamp.security.LampToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LampMemberService extends ServiceImpl<LampMemberMapper, LampMember> {

    @Autowired
    private LampMemberMapper memberMapper;

    public LampMember login(LampMember member) {
        LambdaQueryWrapper<LampMember> queryWrapper = new LambdaQueryWrapper<>(LampMember.class);
        queryWrapper.eq(LampMember::getEmail, member.getUsername());
        queryWrapper.eq(LampMember::getPassword, member.getPassword());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampMember> memberList = memberMapper.selectList(queryWrapper);
        if (memberList.isEmpty()) {
            log.error("登录错误：{}, {}", member.getEmail(), member.getPassword());
            throw new RuntimeException("登录错误");
        }
        member = memberList.get(0);
        LampToken token = new LampToken();
        token.setToken(member.getId());
        member.setToken(token.getToken());
        return member;
    }
}
