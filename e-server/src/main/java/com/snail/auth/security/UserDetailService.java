package com.snail.auth.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.auth.entity.Permission;
import com.snail.auth.mapper.PermissionMapper;
import com.snail.auth.mapper.UserMapper;
import com.snail.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * 用户信息服务
 * 用于登录验证
 */
@Configuration
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        user.setPassword(new BCryptPasswordEncoder().encode("111111"));
        // 设置用户权限
        List<Permission> permissionList = permissionMapper.permissionListByUserId(user.getId());
        user.setAuthorities(AuthorityUtils.createAuthorityList(permissionList.stream().map(Permission::getUrl).toArray(String[]::new)));
        return user;
    }
}
