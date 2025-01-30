package com.lamp.service.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampNotice;
import com.lamp.mapper.LampNoticeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LampNoticeService extends ServiceImpl<LampNoticeMapper, LampNotice> {
    public List<LampNotice> listByMemberId(Long memberId) {
        LambdaQueryWrapper<LampNotice> queryWrapper = new LambdaQueryWrapper<>(LampNotice.class);
        queryWrapper.and(LambdaQueryWrapper -> LambdaQueryWrapper
                .eq(LampNotice::getMemberId, memberId)
                .or()
                .isNull(LampNotice::getMemberId)
        );
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return list(queryWrapper);
    }
}
