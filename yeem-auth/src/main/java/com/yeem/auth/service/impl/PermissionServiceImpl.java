package com.yeem.auth.service.impl;

import com.yeem.auth.service.IPermissionService;
import com.yeem.auth.mapper.PermissionMapper;
import com.yeem.auth.entity.Permission;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
