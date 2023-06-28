package com.snail.auth.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.auth.dto.WxLoginDTO;
import com.snail.auth.dto.WxLoginResponse;
import com.snail.auth.entity.User;
import com.snail.auth.service.IUserService;
import com.snail.auth.service.IZeroAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;

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
    @Value("${security.oauth2.resource.jwt.key-uri}")
    private String oauthTokenUrl;

    @Autowired
    private IUserService userService;

    @Override
    public void signupOrLogin(WxLoginDTO wxLoginDTO) {
        String openId = null;
        String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appId +
                "&secret=" + appSecret +
                "&js_code=" + wxLoginDTO.getCode() +
                "&grant_type=authorization_code";
        log.info("request wx login api url:{}", wxLoginUrl);
        String wxLoginApiResponse = HttpRequest.get(wxLoginUrl).execute().body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            WxLoginResponse wxLoginResponse = objectMapper.readValue(wxLoginApiResponse, WxLoginResponse.class);
            log.info("wx login api response:{}", wxLoginResponse.getOpenid());
            openId = wxLoginResponse.getOpenid();
        } catch (IOException e) {
            log.error("wx login api error：", e);
        }
        if (StringUtils.isEmpty(openId)) {
            throw new RuntimeException("get openId error");
        }
        // todo 校验当前openId是否注册
        // 未注册执行注册逻辑
        User checkUser = usersService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (!StringUtils.isEmpty(checkUser)) {
            return new ResponseEntity<>("用户名重复", HttpStatus.EXPECTATION_FAILED);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        usersService.save(user);
        String auth = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        String authUrl = oauthTokenUrl
                + "?grant_type=password&scope=all"
                + "&username=" + appId + "_" + openId
                + "&password=" + openId;
        HttpResponse httpResponse = HttpRequest.post(authUrl)
                .header("Authorization", auth).execute();
    }
}
