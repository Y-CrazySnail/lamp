package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampService;
import com.lamp.mapper.LampMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
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

    @Override
    public boolean save(LampMember entity) {
        super.save(entity);
        if (Objects.nonNull(entity.getServiceList()) && !entity.getServiceList().isEmpty()) {
            entity.getServiceList().forEach(service -> service.setMemberId(entity.getId()));
            serviceService.saveOrUpdateBatch(entity.getServiceList());
        }
        return true;
    }

    @Override
    public boolean updateById(LampMember entity) {
        super.updateById(entity);
        if (Objects.nonNull(entity.getServiceList()) && !entity.getServiceList().isEmpty()) {
            entity.getServiceList().forEach(service -> service.setMemberId(entity.getId()));
            serviceService.saveOrUpdateBatch(entity.getServiceList());
        }
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        LambdaUpdateWrapper<LampMember> updateWrapper = new LambdaUpdateWrapper<>(LampMember.class);
        updateWrapper.set(LampMember::getDeleteFlag, 1);
        updateWrapper.eq(LampMember::getId, id);
        return SqlHelper.retBool(memberMapper.update(null, updateWrapper));
    }
}
