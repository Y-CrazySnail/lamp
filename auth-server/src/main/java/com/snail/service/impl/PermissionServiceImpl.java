package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.Role;
import com.snail.entity.RolePermission;
import com.snail.entity.User;
import com.snail.mapper.PermissionMapper;
import com.snail.entity.Permission;
import com.snail.mapper.UserMapper;
import com.snail.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
