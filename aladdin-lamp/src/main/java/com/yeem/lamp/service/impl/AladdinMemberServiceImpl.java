package com.yeem.lamp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.entity.Member;
import com.yeem.lamp.mapper.AladdinMemberMapper;
import com.yeem.lamp.service.IAladdinMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("proxy")
@Service
public class AladdinMemberServiceImpl extends ServiceImpl<AladdinMemberMapper, AladdinMember> implements IAladdinMemberService {
    @Override
    public List<AladdinMember> list() {
        QueryWrapper<AladdinMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return super.list(queryWrapper);
    }

    @Override
    public AladdinMember getByUUID(String uuid) {
        QueryWrapper<AladdinMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        return super.getOne(queryWrapper);
    }
}
