package com.snail.chinaybop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.conreoller.BaseController;
import com.snail.chinaybop.entity.Role;
import com.snail.chinaybop.entity.RolePermission;
import com.snail.chinaybop.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @GetMapping("getPermissionIdList")
    public ResponseEntity<Object> getPermissionIdList(Long roleId) {
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        return ResponseEntity.ok(rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
    }
}
