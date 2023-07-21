package com.yeem.auth.controller;

import com.yeem.conreoller.BaseController;
import com.yeem.auth.entity.Permission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission> {

}
