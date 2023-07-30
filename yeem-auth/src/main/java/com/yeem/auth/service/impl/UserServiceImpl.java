package com.yeem.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.auth.mapper.UserRoleMapper;
import com.yeem.auth.service.IUserService;
import com.yeem.auth.entity.UserRole;
import com.yeem.auth.mapper.UserMapper;
import com.yeem.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean save(User entity) {
        userMapper.insert(entity);
        if (!StringUtils.isEmpty(entity.getRoleIdList()) && !entity.getRoleIdList().isEmpty()) {
            entity.getRoleIdList().forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(entity.getId());
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            });
        }
        return true;
    }

    @Override
    public boolean updateById(User entity) {
        if (!StringUtils.isEmpty(entity.getRoleIdList())) {
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", entity.getId()));
            entity.getRoleIdList().forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(entity.getId());
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            });
        }
        userMapper.updateById(entity);
        return true;
    }
}
