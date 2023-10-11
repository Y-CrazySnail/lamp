package com.yeem.zero.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroUserExtraMapper;
import com.yeem.zero.service.IZeroOrderService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ZeroUserExtraServiceImpl extends ServiceImpl<ZeroUserExtraMapper, ZeroUserExtra> implements IZeroUserExtraService {

    @Autowired
    private ZeroUserExtraMapper zeroUserExtraMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private IZeroOrderService zeroOrderService;

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
        if (!StringUtils.isEmpty(userExtra) && userExtra.getDistributionFlag() == 1) {
            Integer referrerUserCount = 0;
            Integer referrerOrderCount = 0;
            // 直接推荐
            if (Objects.equals(Constant.BOOLEAN_TRUE, environment.getProperty("distribution.direct.switch"))) {
                referrerUserCount += getDirectReferrerUserCount(username);
                referrerOrderCount += zeroOrderService.getDirectReferrerOrderCount(username);
            }
            // 间接推荐
            if (Objects.equals(Constant.BOOLEAN_TRUE, environment.getProperty("distribution.direct.switch"))) {
                referrerUserCount += getIndirectReferrerUserCount(username);
                referrerOrderCount += zeroOrderService.getIndirectReferrerOrderCount(username);
            }
            userExtra.setReferrerUserCount(referrerUserCount);
            userExtra.setReferrerOrderCount(referrerOrderCount);
        }
        return userExtra;
    }

    @Override
    public ZeroUserExtra getByUserId(Long userId) {
        QueryWrapper<ZeroUserExtra> userExtraQueryWrapper = new QueryWrapper<>();
        userExtraQueryWrapper.eq("user_id", userId);
        return zeroUserExtraMapper.selectOne(userExtraQueryWrapper);
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

    @Override
    public List<ZeroUserExtra> distribution(String username, String nickName) {
        return baseMapper.distribution(username, nickName);
    }

    @Override
    public Integer getDirectReferrerUserCount(String username) {
        QueryWrapper<ZeroUserExtra> zeroUserExtraQueryWrapper = new QueryWrapper<>();
        zeroUserExtraQueryWrapper.eq("direct_referrer_username", username);
        zeroUserExtraQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        return zeroUserExtraMapper.selectCount(zeroUserExtraQueryWrapper);
    }

    @Override
    public Integer getIndirectReferrerUserCount(String username) {
        QueryWrapper<ZeroUserExtra> zeroUserExtraQueryWrapper = new QueryWrapper<>();
        zeroUserExtraQueryWrapper.eq("indirect_referrer_username", username);
        zeroUserExtraQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        return zeroUserExtraMapper.selectCount(zeroUserExtraQueryWrapper);
    }

    @Override
    public void addBalance(String username, BigDecimal amount) {
        zeroUserExtraMapper.addBalance(username, amount);
    }

    @Override
    public void subtractBalance(String username, BigDecimal amount) {
        zeroUserExtraMapper.subtractBalance(username, amount);
    }

    @Override
    public void addTodoBalance(String username, BigDecimal amount) {
        zeroUserExtraMapper.addTodoBalance(username, amount);
    }

    @Override
    public void subtractTodoBalance(String username, BigDecimal amount) {
        zeroUserExtraMapper.subtractTodoBalance(username, amount);
    }
}
