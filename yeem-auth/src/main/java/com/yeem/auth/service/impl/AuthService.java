package com.yeem.auth.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeem.auth.dto.WxLoginDTO;
import com.yeem.auth.entity.User;
import com.yeem.auth.service.IAuthService;
import com.yeem.auth.service.IUserService;
import com.yeem.dto.PhoneNumberDTO;
import com.yeem.dto.WxLoginResponse;
import com.yeem.utils.WechatUtils;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class AuthService implements IAuthService {

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
    private IZeroUserExtraService zeroUserExtraService;

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    /**
     * WeChat login or signup
     *
     * @param wxLoginDTO wxLoginDto
     * @return login token info
     */
}
