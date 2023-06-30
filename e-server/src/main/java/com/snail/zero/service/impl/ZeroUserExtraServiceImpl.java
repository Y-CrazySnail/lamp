package com.snail.zero.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.zero.entity.ZeroUserExtra;
import com.snail.zero.mapper.ZeroUserExtraMapper;
import com.snail.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class ZeroUserExtraServiceImpl extends ServiceImpl<ZeroUserExtraMapper, ZeroUserExtra> implements IZeroUserExtraService {

    @Autowired
    private ZeroUserExtraMapper zeroUserExtraMapper;

    @DS("zero")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean save(ZeroUserExtra entity) {
        return super.save(entity);
    }

    /**
     * Get user info
     *
     * @param username username
     * @return user info
     */
    @Override
    public ZeroUserExtra info(String username) {
        log.info("get user info. username:{}", username);
        QueryWrapper<ZeroUserExtra> userExtraQueryWrapper = new QueryWrapper<>();
        userExtraQueryWrapper.eq("username", username);
        ZeroUserExtra userExtra = zeroUserExtraMapper.selectOne(userExtraQueryWrapper);
        if (StringUtils.isEmpty(userExtra)) {
            throw new RuntimeException("get user info error");
        }
        return userExtra;
    }
}
