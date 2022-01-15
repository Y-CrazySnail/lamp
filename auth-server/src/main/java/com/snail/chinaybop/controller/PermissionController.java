package com.snail.chinaybop.controller;

import com.snail.conreoller.BaseController;
import com.snail.chinaybop.entity.Permission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission> {

}
