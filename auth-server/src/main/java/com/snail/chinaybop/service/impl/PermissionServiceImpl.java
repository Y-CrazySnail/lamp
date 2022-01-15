package com.snail.chinaybop.service.impl;

import com.snail.chinaybop.mapper.PermissionMapper;
import com.snail.chinaybop.entity.Permission;
import com.snail.chinaybop.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
