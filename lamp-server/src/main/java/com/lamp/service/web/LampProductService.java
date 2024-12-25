package com.lamp.service.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampProduct;
import com.lamp.mapper.LampProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LampProductService extends ServiceImpl<LampProductMapper, LampProduct> {

    @Autowired
    private LampProductMapper productMapper;

    @Autowired
    private LampMemberService memberService;

    public List<LampProduct> listByMemberId(Long memberId) {
        LampMember member = memberService.getById(memberId);
        LambdaQueryWrapper<LampProduct> queryWrapper = new LambdaQueryWrapper<>(LampProduct.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(LampProduct::getMemberLevel, member.getLevel());
        return baseMapper.selectList(queryWrapper);
    }
}
