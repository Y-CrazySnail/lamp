package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinService;
import com.yeem.lamp.mapper.AladdinServiceMapper;
import com.yeem.lamp.service.IAladdinServiceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AladdinServiceServiceImpl extends ServiceImpl<AladdinServiceMapper, AladdinService> implements IAladdinServiceService {
    @Override
    public List<AladdinService> list() {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        // 临时只生成15G流量的用户
        queryWrapper.eq("data_traffic", 15);
        queryWrapper.ge("end_date", new Date());
        return super.list(queryWrapper);
    }

    @Override
    public List<AladdinService> listByMemberId(Long memberId) {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.ge("end_date", new Date());
        queryWrapper.eq("member_id", memberId);
        return super.list(queryWrapper);
    }
}
