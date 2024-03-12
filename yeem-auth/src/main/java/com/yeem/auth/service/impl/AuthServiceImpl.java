package com.yeem.auth.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yeem.common.dto.WechatMiniProgramDTO;
import com.yeem.auth.entity.ThirdPartyLogin;
import com.yeem.auth.entity.User;
import com.yeem.auth.security.VerificationCodeCache;
import com.yeem.auth.service.IAuthService;
import com.yeem.auth.service.IThirdPartyLoginService;
import com.yeem.auth.service.IUserService;
import com.yeem.common.dto.PhoneNumberDTO;
import com.yeem.common.dto.WxLoginResponse;
import com.yeem.common.utils.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private IUserService userService;
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    @Autowired
    private Environment environment;
    @Autowired
    private IThirdPartyLoginService thirdPartyLoginService;

    @Override
    public WechatMiniProgramDTO miniProgram(WechatMiniProgramDTO wechatMiniProgramDTO) {
        String appId = environment.getProperty("wechat." + wechatMiniProgramDTO.getApplication() + ".app-id");
        String appSecret = environment.getProperty("wechat." + wechatMiniProgramDTO.getApplication() + ".app-secret");
        String clientId = environment.getProperty("oauth-client-details." + wechatMiniProgramDTO.getApplication() + ".client-id");
        String clientSecret = environment.getProperty("oauth-client-details." + wechatMiniProgramDTO.getApplication() + ".client-secret");
        String oauthTokenUrl = environment.getProperty("security.oauth2.login-uri");
        String openId = null;
        String sessionKey = null;
        String phoneNumber = null;
        User user = new User();
        try {
            WxLoginResponse wxLoginResponse = WechatUtils.getWxLoginResponse(appId, appSecret, wechatMiniProgramDTO.getCode());
            log.info("wx login api response:{}", wxLoginResponse.getOpenid());
            openId = wxLoginResponse.getOpenid();
            sessionKey = wxLoginResponse.getSession_key();
            log.info("openId:{}", openId);
            log.info("sessionKey:{}", sessionKey);
        } catch (IOException e) {
            log.error("wx login api error：", e);
        }
        if (StrUtil.isEmpty(openId) || StrUtil.isEmpty(sessionKey)) {
            throw new RuntimeException("get openId and sessionKey error");
        }
//        try {
//            PhoneNumberDTO phoneNumberDTO = WechatUtils.decryptPhoneNumber(sessionKey, wechatMiniProgramDTO.getEncryptedData(), wechatMiniProgramDTO.getIv());
//            phoneNumber = phoneNumberDTO.getPhoneNumber();
//        } catch (Exception e) {
//            log.error("decryption phone number error:", e);
//        }
//        if (StrUtil.isEmpty(phoneNumber)) {
//            throw new RuntimeException("decryption phone number error");
//        }
        ThirdPartyLogin thirdPartyLoginWechatMiniProgram = thirdPartyLoginService.getByWechatMiniProgram(wechatMiniProgramDTO.getApplication(), openId);
        if (null == thirdPartyLoginWechatMiniProgram) {
            String usernameTemp = UUID.fastUUID().toString();
            user.setUsername(usernameTemp);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            user.setCredentialsNonExpired(true);
            userService.save(user);
            user.setUsername("yeem" + user.getId());
            user.setPassword(new BCryptPasswordEncoder().encode("yeem" + user.getId()));
            userService.updateById(user);
            thirdPartyLoginService.saveByWechatMiniProgram(user.getId(), wechatMiniProgramDTO.getApplication(), openId);
        } else {
            user = userService.getById(thirdPartyLoginWechatMiniProgram.getUserId());
        }
        String verificationCode = UUID.fastUUID().toString();
        VerificationCodeCache.put(user.getUsername(), verificationCode);
        String auth = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        String authUrl = oauthTokenUrl + "?grant_type=password&scope=all" + "&username=" + user.getUsername() + "&password=" + verificationCode;
        HttpResponse httpResponse = HttpRequest.post(authUrl).header("Authorization", auth).execute();
        wechatMiniProgramDTO.setUserId(user.getId());
        wechatMiniProgramDTO.setUsername(user.getUsername());
//        wechatMiniProgramDTO.setPhoneNumber(phoneNumber);
        wechatMiniProgramDTO.setResponse(httpResponse.body());
        wechatMiniProgramDTO.setWechatOpenId(openId);
        return wechatMiniProgramDTO;
    }
}
