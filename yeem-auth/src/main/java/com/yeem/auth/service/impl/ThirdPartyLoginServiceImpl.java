package com.yeem.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yeem.auth.entity.ThirdPartyLogin;
import com.yeem.auth.mapper.ThirdPartyLoginMapper;
import com.yeem.auth.service.IThirdPartyLoginService;
import com.yeem.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyLoginServiceImpl extends ServiceImpl<ThirdPartyLoginMapper, ThirdPartyLogin> implements IThirdPartyLoginService {

    private static final String LOGIN_TYPE_PHONE = "phone";
    private static final String LOGIN_TYPE_WECHAT_MINI_PROGRAM = "wechat-mini-program";

    @Autowired
    private ThirdPartyLoginMapper thirdPartyLoginMapper;

    @Override
    public ThirdPartyLogin getByPhone(String application, String phone) {
        QueryWrapper<ThirdPartyLogin> thirdPartyLoginQueryWrapper = new QueryWrapper<>();
        thirdPartyLoginQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        thirdPartyLoginQueryWrapper.eq("application", application);
        thirdPartyLoginQueryWrapper.eq("login_type", LOGIN_TYPE_PHONE);
        thirdPartyLoginQueryWrapper.eq("login_name", phone);
        return thirdPartyLoginMapper.selectOne(thirdPartyLoginQueryWrapper);
    }

    @Override
    public ThirdPartyLogin getByWechatMiniProgram(String application, String openId) {
        QueryWrapper<ThirdPartyLogin> thirdPartyLoginQueryWrapper = new QueryWrapper<>();
        thirdPartyLoginQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), 0);
        thirdPartyLoginQueryWrapper.eq("application", application);
        thirdPartyLoginQueryWrapper.eq("login_type", LOGIN_TYPE_WECHAT_MINI_PROGRAM);
        thirdPartyLoginQueryWrapper.eq("login_name", openId);
        return thirdPartyLoginMapper.selectOne(thirdPartyLoginQueryWrapper);
    }

    @Override
    public boolean saveByWechatMiniProgram(Long userId, String application, String openId) {
        ThirdPartyLogin thirdPartyLogin = new ThirdPartyLogin();
        thirdPartyLogin.setUserId(userId);
        thirdPartyLogin.setApplication(application);
        thirdPartyLogin.setLoginType(LOGIN_TYPE_WECHAT_MINI_PROGRAM);
        thirdPartyLogin.setLoginName(openId);
        return SqlHelper.retBool(thirdPartyLoginMapper.insert(thirdPartyLogin));
    }
}
