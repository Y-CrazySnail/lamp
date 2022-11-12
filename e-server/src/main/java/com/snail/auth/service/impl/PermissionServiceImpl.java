package com.snail.auth.service.impl;

import com.snail.auth.service.IPermissionService;
import com.snail.auth.mapper.PermissionMapper;
import com.snail.auth.entity.Permission;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
