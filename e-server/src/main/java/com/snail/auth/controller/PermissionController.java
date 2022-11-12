package com.snail.auth.controller;

import com.snail.conreoller.BaseController;
import com.snail.auth.entity.Permission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission> {

}
