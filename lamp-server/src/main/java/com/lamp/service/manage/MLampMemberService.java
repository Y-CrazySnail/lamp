package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.mapper.LampMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Slf4j
@Service
public class MLampMemberService extends ServiceImpl<LampMemberMapper, LampMember> {

    @Autowired
    private LampMemberMapper memberMapper;

    @Override
    public <E extends IPage<LampMember>> E page(E page, Wrapper<LampMember> queryWrapper) {
        page = super.page(page, queryWrapper);
        return page;
    }

    @Override
    public boolean removeById(Serializable id) {
        LambdaUpdateWrapper<LampMember> updateWrapper = new LambdaUpdateWrapper<>(LampMember.class);
        updateWrapper.set(LampMember::getDeleteFlag, 1);
        updateWrapper.eq(LampMember::getId, id);
        return SqlHelper.retBool(memberMapper.update(null, updateWrapper));
    }
}
