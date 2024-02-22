package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneTenant;
import com.yeem.one.mapper.OneTenantMapper;
import com.yeem.one.service.IOneTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OneTenantServiceImpl extends ServiceImpl<OneTenantMapper, OneTenant> implements IOneTenantService {

}
