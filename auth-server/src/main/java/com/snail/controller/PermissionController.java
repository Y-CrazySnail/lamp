package com.snail.controller;

import com.snail.conreoller.BaseController;
import com.snail.entity.Permission;
import com.snail.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission> {

}
