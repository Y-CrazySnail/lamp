package com.lamp.service.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampService;
import com.lamp.mapper.LampServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LampServiceService extends ServiceImpl<LampServiceMapper, LampService> {

    @Autowired
    private LampServiceMapper serviceMapper;

    public List<LampService> listByMemberId(Long memberId) {
        LambdaQueryWrapper<LampService> queryWrapper = new LambdaQueryWrapper<>(LampService.class);
        queryWrapper.eq(LampService::getMemberId, memberId);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return serviceMapper.selectList(queryWrapper);
    }

    public LampService getByUUID(String uuid) {
        LambdaQueryWrapper<LampService> queryWrapper = new LambdaQueryWrapper<>(LampService.class);
        queryWrapper.eq(LampService::getUuid, uuid);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return serviceMapper.selectOne(queryWrapper);
    }

}
