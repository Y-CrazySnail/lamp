package com.yeem.car.service.wechat;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFUser;
import com.yeem.car.mapper.CFUserMapper;
import com.yeem.common.dto.WxLoginResponse;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.WechatJWTUtils;
import com.yeem.common.utils.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class WechatCFUserService extends ServiceImpl<CFUserMapper, CFUser> {

    public static final String DICT_TYPE_APP_ID = "app-id";
    public static final String DICT_TYPE_APP_SECRET = "app-secret";

    @Autowired
    private CFUserMapper userMapper;

    @Autowired
    private WechatBCDictionaryService dictionaryService;

    /**
     * @param user 登录用户信息
     *             tenantNo 客户代码
     *             code 小程序登录代码
     * @return 登录信息
     */
    public CFUser login(CFUser user) {
        String appId = dictionaryService.list(DICT_TYPE_APP_ID, user.getTenantNo()).get(0).getDictValue();
        String appSecret = dictionaryService.list(DICT_TYPE_APP_SECRET, user.getTenantNo()).get(0).getDictValue();
        String openId;
        WxLoginResponse wxLoginResponse = null;
        try {
            wxLoginResponse = WechatUtils.wechatLogin(appId, appSecret, user.getCode());
        } catch (IOException e) {
            log.error("小程序认证失败：", e);
        }
        if (null == wxLoginResponse || StrUtil.isEmpty(wxLoginResponse.getOpenid())) {
            throw new RuntimeException("小程序认证失败");
        }
        openId = wxLoginResponse.getOpenid();
        log.info("openId:{}", openId);
        CFUser checkUser = get(user.getTenantNo(), openId);
        if (Objects.isNull(checkUser) || Objects.isNull(checkUser.getId())) {
            user.setOpenId(openId);
            this.save(user);
        }
        user = get(user.getTenantNo(), openId);
        String token = WechatJWTUtils.generateJWT(user.getTenantNo(), user.getId(), user.getOpenId());
        user.setToken(token);
        return user;
    }

    public CFUser get(String tenantNo, String openId) {
        LambdaQueryWrapper<CFUser> queryWrapper = Wrappers.lambdaQuery(CFUser.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        queryWrapper.eq(CFUser::getTenantNo, tenantNo);
        queryWrapper.eq(CFUser::getOpenId, openId);
        return userMapper.selectOne(queryWrapper);
    }
}
