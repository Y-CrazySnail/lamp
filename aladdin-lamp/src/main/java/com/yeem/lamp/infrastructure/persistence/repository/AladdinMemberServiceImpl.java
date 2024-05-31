package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.security.Constant;
import com.yeem.lamp.infrastructure.persistence.entity.MemberEntity;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.MemberMapper;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinMemberService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinOrderService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;

import java.io.Serializable;
import java.util.List;

@Service
public class AladdinMemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements IAladdinMemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IAladdinServiceService aladdinServiceService;
    @Autowired
    private IAladdinOrderService aladdinOrderService;

    @Override
    public List<MemberEntity> list() {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return super.list(queryWrapper);
    }

    @Override
    public MemberEntity getByUUID(String uuid) {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        return super.getOne(queryWrapper);
    }

    @Override
    public IPage<MemberEntity> pages(int current, int size, String email, String wechat) {
        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        if (!StrUtil.isEmpty(email)) {
            queryWrapper.like("email", email);
        }
        if (!StrUtil.isEmpty(wechat)) {
            queryWrapper.like("wechat", wechat);
        }
        IPage<MemberEntity> page = new Page<>(current, size);
        return memberMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 删除会员信息
        UpdateWrapper<MemberEntity> memberUpdateWrapper = new UpdateWrapper<>();
        memberUpdateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        memberUpdateWrapper.eq(BaseEntity.BaseField.ID.getName(), id);
        memberMapper.update(null, memberUpdateWrapper);
        // 删除服务信息
        boolean serviceStatus = aladdinServiceService.removeByMemberId(id);
        // 删除订单信息
        boolean orderStatus = aladdinOrderService.removeByMemberId(id);
        return super.removeById(id);
    }
}
