package com.yeem.lamp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinOrder;
import com.yeem.lamp.entity.AladdinService;
import com.yeem.lamp.mapper.AladdinOrderMapper;
import com.yeem.lamp.service.IAladdinOrderService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class AladdinOrderServiceImpl extends ServiceImpl<AladdinOrderMapper, AladdinOrder> implements IAladdinOrderService {

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<AladdinOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }
}
