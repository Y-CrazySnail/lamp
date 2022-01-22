package com.snail.service.impl;

import com.snail.mapper.PermissionMapper;
import com.snail.entity.Permission;
import com.snail.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
