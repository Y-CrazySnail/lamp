package com.snail.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.conreoller.BaseController;
import com.snail.entity.RolePermission;
import com.snail.entity.User;
import com.snail.entity.UserRole;
import com.snail.mapper.RolePermissionMapper;
import com.snail.mapper.UserRoleMapper;
import com.snail.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    @Autowired
    private IUserService usersService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * @param user 用户
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Object> signIn(@RequestBody @Validated User user) {
        User checkUser = usersService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (!StringUtils.isEmpty(checkUser)) {
            return new ResponseEntity<>("用户名重复", HttpStatus.EXPECTATION_FAILED);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        usersService.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("info")
    public Authentication info() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("getRoleIdList")
    public ResponseEntity<Object> getRoleIdList(Long userId) {
        List<UserRole> rolePermissionList = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
        return ResponseEntity.ok(rolePermissionList.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
    }
}
