package com.snail.chinaybop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.chinaybop.entity.RolePermission;
import com.snail.chinaybop.mapper.RoleMapper;
import com.snail.chinaybop.entity.Role;
import com.snail.chinaybop.mapper.RolePermissionMapper;
import com.snail.chinaybop.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(Role entity) {
        roleMapper.insert(entity);
        entity.getPermissionIdList().forEach(permissionId -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(entity.getId());
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.insert(rolePermission);
        });
        return true;
    }

    @Override
    public boolean updateById(Role entity) {
        rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", entity.getId()));
        entity.getPermissionIdList().forEach(permissionId -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(entity.getId());
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.insert(rolePermission);
        });
        roleMapper.updateById(entity);
        return true;
    }
}