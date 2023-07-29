package com.yeem.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.entity.BaseEntity;

@TableName(value = "sys_role_permission", autoResultMap = true)
public class RolePermission extends BaseEntity {
    private Long roleId;
    private Long permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}