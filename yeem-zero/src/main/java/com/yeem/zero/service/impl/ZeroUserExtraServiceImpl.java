package com.yeem.zero.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.ZeroAddress;
import com.yeem.zero.entity.ZeroBalanceRecord;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroUserExtraMapper;
import com.yeem.zero.service.IZeroAddressService;
import com.yeem.zero.service.IZeroBalanceRecordService;
import com.yeem.zero.service.IZeroOrderService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

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
    @Autowired
    private IZeroAddressService zeroAddressService;
    @Autowired
    private IZeroBalanceRecordService zeroBalanceRecordService;

    @DS("zero")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean save(ZeroUserExtra entity) {
        return super.save(entity);
    }

    /**
     * Get user info
     *
     * @param userId userId
     * @return user info
     */
    @Override
    public ZeroUserExtra getById(Long userId) {
        log.info("get user info. user id:{}", userId);
        ZeroUserExtra userExtra = zeroUserExtraMapper.selectById(userId);
        if (null != userExtra && userExtra.getDistributionFlag() == 1) {
            Integer referrerUserCount = 0;
            Integer referrerOrderCount = 0;
            // 直接推荐
            if (Objects.equals(Constant.BOOLEAN_TRUE, environment.getProperty("distribution.direct.switch"))) {
                referrerUserCount += getDirectReferrerUserCount(userId);
                referrerOrderCount += zeroOrderService.getDirectReferrerOrderCount(userId);
            }
            // 间接推荐
            if (Objects.equals(Constant.BOOLEAN_TRUE, environment.getProperty("distribution.direct.switch"))) {
                referrerUserCount += getIndirectReferrerUserCount(userId);
                referrerOrderCount += zeroOrderService.getIndirectReferrerOrderCount(userId);
            }
            userExtra.setReferrerUserCount(referrerUserCount);
            userExtra.setReferrerOrderCount(referrerOrderCount);
        }
        List<ZeroAddress> zeroAddressList = zeroAddressService.listByUserId(userId);
        userExtra.setZeroAddressList(zeroAddressList);
        return userExtra;
    }

    @Override
    public ZeroUserExtra getDetailById(Long userId) {
        log.info("get user detail info. user id:{}", userId);
        ZeroUserExtra userExtra = zeroUserExtraMapper.selectById(userId);
        if (null != userExtra && userExtra.getDistributionFlag() == 1) {
            Integer referrerUserCount = 0;
            Integer referrerOrderCount = 0;
            // 直接推荐
            if (Objects.equals(Constant.BOOLEAN_TRUE, environment.getProperty("distribution.direct.switch"))) {
                referrerUserCount += getDirectReferrerUserCount(userId);
                referrerOrderCount += zeroOrderService.getDirectReferrerOrderCount(userId);
            }
            // 间接推荐
            if (Objects.equals(Constant.BOOLEAN_TRUE, environment.getProperty("distribution.direct.switch"))) {
                referrerUserCount += getIndirectReferrerUserCount(userId);
                referrerOrderCount += zeroOrderService.getIndirectReferrerOrderCount(userId);
            }
            userExtra.setReferrerUserCount(referrerUserCount);
            userExtra.setReferrerOrderCount(referrerOrderCount);
        }
        // 地址处理
        List<ZeroAddress> zeroAddressList = zeroAddressService.listByUserId(userId);
        userExtra.setZeroAddressList(zeroAddressList);
        // 获取零钱明细
        List<ZeroBalanceRecord> zeroBalanceRecordList = zeroBalanceRecordService.listByUserId(userId);
        userExtra.setZeroBalanceRecordList(zeroBalanceRecordList);
        // 申请成为分销商 判断身份证号数量
        if (Constant.DISTRIBUTION_FLAG_ING.equals(userExtra.getDistributionFlag())) {
            int distributionUserCount = zeroUserExtraMapper.distributionUserCountByIdCardNo(userExtra.getIdCardNo());
            userExtra.setRepeatApplyDistributionCount(distributionUserCount);
        }
        return userExtra;
    }

    @Override
    public ZeroUserExtra getByWechatOpenId(String openId) {
        QueryWrapper<ZeroUserExtra> userExtraQueryWrapper = new QueryWrapper<>();
        userExtraQueryWrapper.eq("wechat_open_id", openId);
        return zeroUserExtraMapper.selectOne(userExtraQueryWrapper);
    }

    @Override
    public List<ZeroUserExtra> distribution(Long userId, String nickName) {
        return baseMapper.distribution(userId, nickName);
    }

    @Override
    public Integer getDirectReferrerUserCount(Long userId) {
        QueryWrapper<ZeroUserExtra> zeroUserExtraQueryWrapper = new QueryWrapper<>();
        zeroUserExtraQueryWrapper.eq("direct_referrer_user_id", userId);
        zeroUserExtraQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        return zeroUserExtraMapper.selectCount(zeroUserExtraQueryWrapper);
    }

    @Override
    public Integer getIndirectReferrerUserCount(Long userId) {
        QueryWrapper<ZeroUserExtra> zeroUserExtraQueryWrapper = new QueryWrapper<>();
        zeroUserExtraQueryWrapper.eq("indirect_referrer_user_id", userId);
        zeroUserExtraQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        return zeroUserExtraMapper.selectCount(zeroUserExtraQueryWrapper);
    }

    @Override
    public void addBalance(Long userId, BigDecimal amount) {
        zeroUserExtraMapper.addBalance(userId, amount);
    }

    @Override
    public void subtractBalance(Long userId, BigDecimal amount) {
        zeroUserExtraMapper.subtractBalance(userId, amount);
    }

    @Override
    public void addTodoBalance(Long userId, BigDecimal amount) {
        zeroUserExtraMapper.addTodoBalance(userId, amount);
    }

    @Override
    public void subtractTodoBalance(Long userId, BigDecimal amount) {
        zeroUserExtraMapper.subtractTodoBalance(userId, amount);
    }

    @Override
    public List<ZeroUserExtra> distributionUserList(String nickName) {
        QueryWrapper<ZeroUserExtra> zeroUserExtraQueryWrapper = new QueryWrapper<>();
        zeroUserExtraQueryWrapper.eq("distribution_flag", Constant.BOOLEAN_TRUE);
        if (!StrUtil.isEmpty(nickName)) {
            zeroUserExtraQueryWrapper.like("nick_name", nickName);
        }
        return zeroUserExtraMapper.selectList(zeroUserExtraQueryWrapper);
    }
}
