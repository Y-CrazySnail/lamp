package com.yeem.zero.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.utils.OauthUtils;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroUserExtraMapper;
import com.yeem.zero.service.IZeroUserExtraService;
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
    public ZeroUserExtra get(String username) {
        log.info("get user info. username:{}", username);
        QueryWrapper<ZeroUserExtra> userExtraQueryWrapper = new QueryWrapper<>();
        userExtraQueryWrapper.eq("username", username);
        ZeroUserExtra userExtra = zeroUserExtraMapper.selectOne(userExtraQueryWrapper);
        if (StringUtils.isEmpty(userExtra)) {
            throw new RuntimeException("get user info error");
        }
        return userExtra;
    }

    /**
     * Update user info
     *
     * @param zeroUserExtra user info
     * @return status
     */
    @Override
    public ZeroUserExtra update(ZeroUserExtra zeroUserExtra) {
        UpdateWrapper<ZeroUserExtra> zeroUserExtraUpdateWrapper = new UpdateWrapper<>();
        zeroUserExtraUpdateWrapper.eq("username", OauthUtils.getUsername());
        zeroUserExtraMapper.update(zeroUserExtra, zeroUserExtraUpdateWrapper);
        return zeroUserExtra;
    }
}
