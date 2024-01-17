package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.entity.AladdinService;
import com.yeem.lamp.mapper.AladdinServiceMapper;
import com.yeem.lamp.service.IAladdinServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service
public class AladdinServiceServiceImpl extends ServiceImpl<AladdinServiceMapper, AladdinService> implements IAladdinServiceService {

    @Autowired
    private AladdinServiceMapper aladdinServiceMapper;

    @Override
    public List<AladdinService> list() {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        // 临时只生成15G 50G流量的用户
        queryWrapper.lt("data_traffic", 100);
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

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<AladdinService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }

    @Override
    public IPage<AladdinService> pages(int current, int size, Long memberId, String status) {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        if (!StringUtils.isEmpty(memberId)) {
            queryWrapper.eq("member_id", memberId);
        }
        if (!StringUtils.isEmpty(status)) {
            if (Constant.TRUE_STRING.equals(status)) {
                queryWrapper.gt("end_date", new Date());
            } else {
                queryWrapper.le("end_date", new Date());
            }
        }
        IPage<AladdinService> page = new Page<>(current, size);
        return aladdinServiceMapper.selectPages(page);
    }
}
