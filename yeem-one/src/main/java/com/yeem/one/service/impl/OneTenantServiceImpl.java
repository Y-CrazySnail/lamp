package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.utils.OauthUtils;
import com.yeem.one.entity.OneTenant;
import com.yeem.one.mapper.OneTenantMapper;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
        return usernameList.contains(username);
    }
}
