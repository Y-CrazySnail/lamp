package com.snail.auth.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.dto.PhoneNumberDTO;
import com.snail.auth.dto.WxLoginDTO;
import com.snail.dto.WxLoginResponse;
import com.snail.auth.entity.User;
import com.snail.auth.entity.UserExtra;
import com.snail.auth.service.IUserExtraService;
import com.snail.auth.service.IUserService;
import com.snail.auth.service.IZeroAuthService;
import com.snail.utils.WechatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Base64;

import java.io.IOException;

@Slf4j
@Service
public class ZeroAuthService implements IZeroAuthService {

    /**
     * 客户端ID
     */
    @Value("${oauth-client-details.zero.client-id}")
    private String clientId;
    /**
     * 客户端Secret
     */
    @Value("${oauth-client-details.zero.client-secret}")
    private String clientSecret;
    /**
     * 微信AppId
     */
    @Value("${wechat.zero.app-id}")
    private String appId;
    /**
     * 微信AppSecret
     */
    @Value("${wechat.zero.app-secret}")
    private String appSecret;
    /**
     * 获取Token地址
     */
    @Value("${security.oauth2.login-uri}")
    private String oauthTokenUrl;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserExtraService userExtraService;

    /**
     * WeChat login or signup
     *
     * @param wxLoginDTO wxLoginDto
     * @return login token info
     */
    @Override
    @Transactional
    public String signupOrLogin(WxLoginDTO wxLoginDTO) {
        String openId = null;
        String sessionKey = null;
        String phoneNumber = null;
        try {
            WxLoginResponse wxLoginResponse = WechatUtil.getWxLoginResponse(appId, appSecret, wxLoginDTO.getCode());
            log.info("wx login api response:{}", wxLoginResponse.getOpenid());
            openId = wxLoginResponse.getOpenid();
            sessionKey = wxLoginResponse.getSession_key();
            log.info("openId:{}", openId);
            log.info("sessionKey:{}", sessionKey);
        } catch (IOException e) {
            log.error("wx login api error：", e);
        }
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(sessionKey)) {
            throw new RuntimeException("get openId and sessionKey error");
        }
        try {
            PhoneNumberDTO phoneNumberDTO = WechatUtil.decryptPhoneNumber(sessionKey, wxLoginDTO.getEncryptedData(), wxLoginDTO.getIv());
            phoneNumber = phoneNumberDTO.getPhoneNumber();
        } catch (Exception e) {
            log.error("decryption phone number error:", e);
        }
        if (StringUtils.isEmpty(phoneNumber)) {
            throw new RuntimeException("decryption phone number error");
        }
        String username = appId + "_" + openId;
        String password = appId + "_" + openId;
        User checkUser = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (StringUtils.isEmpty(checkUser)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            user.setCredentialsNonExpired(true);
            userService.save(user);
            UserExtra userExtra = new UserExtra();
            userExtra.setUserId(user.getId());
            userExtra.setUsername(username);
            userExtra.setNickName(wxLoginDTO.getNickName());
            userExtra.setAvatarUrl(wxLoginDTO.getAvatarUrl());
            userExtra.setGender(wxLoginDTO.getGender());
            userExtra.setPhoneNumber(phoneNumber);
            userExtraService.save(userExtra);
        }
        String auth = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        String authUrl = oauthTokenUrl + "?grant_type=password&scope=all" + "&username=" + username + "&password=" + password;
        HttpResponse httpResponse = HttpRequest.post(authUrl).header("Authorization", auth).execute();
        return httpResponse.body();
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
        UserExtra userExtra = userExtraService.getOne(userExtraQueryWrapper);
        if (StringUtils.isEmpty(userExtra)) {
            throw new RuntimeException("get user info error");
        }
        return userExtra;
    }
}
