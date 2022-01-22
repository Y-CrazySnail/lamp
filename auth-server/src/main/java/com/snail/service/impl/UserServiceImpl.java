package com.snail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.UserRole;
import com.snail.mapper.UserMapper;
import com.snail.mapper.UserRoleMapper;
import com.snail.service.IUserService;
import com.snail.entity.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean save(User entity) {
        userMapper.insert(entity);
        entity.getRoleIdList().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(entity.getId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
        return true;
    }

    @Override
    public boolean updateById(User entity) {
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", entity.getId()));
        entity.getRoleIdList().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(entity.getId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
        userMapper.updateById(entity);
        return true;
    }
}
