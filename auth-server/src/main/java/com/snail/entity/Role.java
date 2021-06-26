package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName(value = "sys_role", autoResultMap = true)
public class Role extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    private String description;
    @TableField(exist = false)
    private List<Long> permissionIdList;
    @TableField(exist = false)
    private List<Permission> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Long> getPermissionIdList() {
        return permissionIdList;
    }

    public void setPermissionIdList(List<Long> permissionIdList) {
        this.permissionIdList = permissionIdList;
    }
}