package com.yeem.lamp.infrastructure.persistence.repository.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.infrastructure.persistence.entity.MemberDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.MemberMapper;
import com.yeem.lamp.security.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemberWebRepository {

    @Autowired
    private MemberMapper memberMapper;

    public Member getById(Long id) {
        MemberDo memberDo = memberMapper.selectById(id);
        return memberDo.convertMember();
    }

    public List<Member> list(Member member) {
        LambdaQueryWrapper<MemberDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberDo::getDeleteFlag, false);
        if (StrUtil.isNotEmpty(member.getEmail())) {
            queryWrapper.eq(MemberDo::getEmail, member.getEmail());
        }
        List<MemberDo> memberDoList = memberMapper.selectList(queryWrapper);
        return memberDoList.stream().map(MemberDo::convertMember).collect(Collectors.toList());
    }
}
