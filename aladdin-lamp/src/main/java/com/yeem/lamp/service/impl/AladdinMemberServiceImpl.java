package com.yeem.lamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.config.Constant;
import com.yeem.lamp.entity.AladdinMember;
import com.yeem.lamp.mapper.AladdinMemberMapper;
import com.yeem.lamp.service.IAladdinMemberService;
import com.yeem.lamp.service.IAladdinOrderService;
import com.yeem.lamp.service.IAladdinServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

@Service
public class AladdinMemberServiceImpl extends ServiceImpl<AladdinMemberMapper, AladdinMember> implements IAladdinMemberService {

    @Autowired
    private AladdinMemberMapper aladdinMemberMapper;
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private IAladdinOrderService aladdinOrderService;

    @Override
    public List<AladdinMember> list() {
        QueryWrapper<AladdinMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return super.list(queryWrapper);
    }

    @Override
    public AladdinMember getByUUID(String uuid) {
        QueryWrapper<AladdinMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        return super.getOne(queryWrapper);
    }

    @Override
    public IPage<AladdinMember> pages(int current, int size, String email, String wechat) {
        QueryWrapper<AladdinMember> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(email)) {
            queryWrapper.like("email", email);
        }
        if (!StringUtils.isEmpty(wechat)) {
            queryWrapper.like("wechat", wechat);
        }
        IPage<AladdinMember> page = new Page<>(current, size);
        return aladdinMemberMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 删除会员信息
        UpdateWrapper<AladdinMember> memberUpdateWrapper = new UpdateWrapper<>();
        memberUpdateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        memberUpdateWrapper.eq(BaseEntity.BaseField.ID.getName(), id);
        aladdinMemberMapper.update(null, memberUpdateWrapper);
        // 删除服务信息
        boolean serviceStatus = aladdinServiceService.removeByMemberId(id);
        // 删除订单信息
        boolean orderStatus = aladdinOrderService.removeByMemberId(id);
        return super.removeById(id);
    }
}
