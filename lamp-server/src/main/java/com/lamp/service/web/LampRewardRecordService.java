package com.lamp.service.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampRewardRecord;
import com.lamp.mapper.LampRewardRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LampRewardRecordService extends ServiceImpl<LampRewardRecordMapper, LampRewardRecord> {

    public void setRewardRecordList(LampMember member) {
        member.setRewardRecordList(this.listByReferrerId(member.getId()));
    }

    public List<LampRewardRecord> listByReferrerId(Long referrerId) {
        LambdaQueryWrapper<LampRewardRecord> queryWrapper = new LambdaQueryWrapper<>(LampRewardRecord.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(LampRewardRecord::getReferrerId, referrerId);
        return baseMapper.selectList(queryWrapper);
    }

}
