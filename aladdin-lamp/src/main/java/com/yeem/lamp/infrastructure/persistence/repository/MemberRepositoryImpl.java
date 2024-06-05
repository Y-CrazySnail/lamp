package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.repository.MemberRepository;
import com.yeem.lamp.infrastructure.persistence.entity.MemberDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.MemberMapper;
import com.yeem.lamp.security.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member get(Member member) {
        LambdaQueryWrapper<MemberDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberDo::getEmail, member.getEmail());
        queryWrapper.eq(MemberDo::getDeleteFlag, false);
        MemberDo memberDo = memberMapper.selectOne(queryWrapper);
        return memberDo.convertMember();
    }

    @Override
    public Member getById(Long id) {
        MemberDo memberDo = memberMapper.selectById(id);
        return memberDo.convertMember();
    }

    @Override
    public List<Member> list() {
        LambdaQueryWrapper<MemberDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberDo::getDeleteFlag, false);
        List<MemberDo> memberDoList = memberMapper.selectList(queryWrapper);
        return memberDoList.stream().map(MemberDo::convertMember).collect(Collectors.toList());
    }

    @Override
    public Member getByUUID(String uuid) {
        QueryWrapper<MemberDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        MemberDo memberDo = memberMapper.selectOne(queryWrapper);
        return memberDo.convertMember();
    }

    @Override
    public IPage<Member> pages(int current, int size, String email, String wechat) {
        QueryWrapper<MemberDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        if (!StrUtil.isEmpty(email)) {
            queryWrapper.like("email", email);
        }
        if (!StrUtil.isEmpty(wechat)) {
            queryWrapper.like("wechat", wechat);
        }
        IPage<MemberDo> page = new Page<>(current, size);
        IPage<Member> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(MemberDo::convertMember).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    @Override
    public void save(Member member) {
        MemberDo memberDo = new MemberDo(member);
        memberMapper.insert(memberDo);
    }

    @Override
    public void updateById(Member member) {
        MemberDo memberDo = new MemberDo(member);
        memberMapper.updateById(memberDo);
    }

    @Override
    public void removeById(Long id) {
        // 删除会员信息
        UpdateWrapper<MemberDo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq(BaseEntity.BaseField.ID.getName(), id);
        memberMapper.update(null, updateWrapper);
    }
}
