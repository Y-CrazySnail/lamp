package com.snail.zero.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.zero.entity.UserExtra;
import com.snail.zero.mapper.UserExtraMapper;
import com.snail.zero.service.IUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@DS("zero")
public class UserExtraServiceImpl extends ServiceImpl<UserExtraMapper, UserExtra> implements IUserExtraService {

    @Autowired
    private UserExtraMapper userExtraMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean save(UserExtra entity) {
        return super.save(entity);
    }

    /**
     * Get user info
     *
     * @param username username
     * @return user info
     */
    @Override
    public UserExtra info(String username) {
        log.info("get user info. username:{}", username);
        QueryWrapper<UserExtra> userExtraQueryWrapper = new QueryWrapper<>();
        userExtraQueryWrapper.eq("username", username);
        UserExtra userExtra = userExtraMapper.selectOne(userExtraQueryWrapper);
        if (StringUtils.isEmpty(userExtra)) {
            throw new RuntimeException("get user info error");
        }
        return userExtra;
    }
}
