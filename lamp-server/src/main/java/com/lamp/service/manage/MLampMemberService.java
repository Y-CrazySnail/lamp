package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampMember;
import com.lamp.mapper.LampMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Objects;

@Slf4j
@Service
public class MLampMemberService extends ServiceImpl<LampMemberMapper, LampMember> {

    @Autowired
    private LampMemberMapper memberMapper;

    @Autowired
    private MLampServiceService serviceService;

    @Override
    public <E extends IPage<LampMember>> E page(E page, Wrapper<LampMember> queryWrapper) {
        page = super.page(page, queryWrapper);
        return page;
    }

    @Override
    public LampMember getById(Serializable id) {
        LampMember member = super.getById(id);
        if (Objects.isNull(member)) {
            return null;
        }
        serviceService.setServiceList(member);
        return member;
    }

    @Transactional
    @Override
    public boolean save(LampMember entity) {
        entity.calculateMonthBandwidth();
        return super.save(entity);
    }

    @Transactional
    @Override
    public boolean updateById(LampMember entity) {
        return super.updateById(entity);
    }

    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        LambdaUpdateWrapper<LampMember> updateWrapper = new LambdaUpdateWrapper<>(LampMember.class);
        updateWrapper.set(LampMember::getDeleteFlag, 1);
        updateWrapper.eq(LampMember::getId, id);
        memberMapper.update(null, updateWrapper);
        return true;
    }

    public void syncBandwidth(Long memberId) {
        if (Objects.nonNull(memberId)) {
            memberMapper.syncBandwidth(memberId);
        } else {
            memberMapper.syncBandwidth(null);
        }
    }
}
