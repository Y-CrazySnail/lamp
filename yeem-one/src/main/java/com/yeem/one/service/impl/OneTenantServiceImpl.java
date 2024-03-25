package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.utils.OauthUtils;
import com.yeem.one.entity.OneTenant;
import com.yeem.one.mapper.OneTenantMapper;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OneTenantServiceImpl extends ServiceImpl<OneTenantMapper, OneTenant> implements IOneTenantService {

    @Autowired
    private OneTenantMapper mapper;

    @Override
    public boolean authenticate(Long tenantId) {
        String username = OauthUtils.getUsername();
        OneTenant oneTenant = mapper.selectById(tenantId);
        List<String> usernameList = Arrays.asList(oneTenant.getBelongUsername().split(";"));
        if (!usernameList.contains(username)) {
            log.error("authenticate fail username:{}, tenant id:{}", username, tenantId);
            throw new RuntimeException("authenticate fail");
        }
        return true;
    }

    @Override
    public Set<Long> authorizedTenantIdSet() {
        String username = OauthUtils.getUsername();
        LambdaQueryWrapper<OneTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(OneTenant::getBelongUsername, ";" + username + ";");
        queryWrapper.eq(OneTenant::getDeleteFlag, false);
        List<OneTenant> tenantList = mapper.selectList(queryWrapper);
        if (tenantList.isEmpty()) {
            return new HashSet<>();
        } else {
            return tenantList.stream().map(OneTenant::getId).collect(Collectors.toSet());
        }
    }

    @Override
    public List<OneTenant> listByUsername(String username) {
        LambdaQueryWrapper<OneTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(OneTenant::getBelongUsername, ";" + username + ";");
        queryWrapper.eq(OneTenant::getDeleteFlag, false);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public OneTenant getByWechatAppId(String wechatAppId) {
        LambdaQueryWrapper<OneTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneTenant::getWechatAppId, wechatAppId);
        queryWrapper.eq(OneTenant::getDeleteFlag, false);
        return mapper.selectOne(queryWrapper);
    }
}
