package com.yeem.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.auth.service.IPermissionService;
import com.yeem.common.conreoller.BaseController;
import com.yeem.auth.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission> {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("all")
    public ResponseEntity<List<Permission>> all() {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.orderByAsc("name");
        return ResponseEntity.ok(permissionService.list(permissionQueryWrapper));
    }
}
