package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<AladdinService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
    }

    @Override
    public IPage<AladdinService> pages(int current, int size) {
        QueryWrapper<AladdinService> queryWrapper = new QueryWrapper<>();
//        if (!StringUtils.isEmpty(email)) {
//            queryWrapper.like("email", email);
//        }
//        if (!StringUtils.isEmpty(wechat)) {
//            queryWrapper.like("wechat", wechat);
//        }
        IPage<AladdinService> page = new Page<>(current, size);
        return aladdinServiceMapper.selectPage(page, queryWrapper);
    }
}
