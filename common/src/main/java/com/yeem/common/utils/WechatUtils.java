package com.yeem.common.utils;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.common.dto.PhoneNumberDTO;
import com.yeem.common.dto.WxLoginResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class WechatUtils {
    /**
     * 获取OpenId
     * @param appId
     * @param appSecret
     * @param code
     * @return
     * @throws IOException
     */
    public static WxLoginResponse wechatLogin(String appId, String appSecret, String code) throws IOException {
        String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appId +
                "&secret=" + appSecret +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        log.info("request wx login api url:{}", wxLoginUrl);
        String wxLoginApiResponse = HttpRequest.get(wxLoginUrl).execute().body();
        ObjectMapper objectMapper = new ObjectMapper();
        WxLoginResponse wxLoginResponse = objectMapper.readValue(wxLoginApiResponse, WxLoginResponse.class);
        log.info("wx login api response:{}", wxLoginResponse.getOpenid());
        return wxLoginResponse;
    }

    /**
     * 获取手机号
     * @param sessionKey sessionKey
     * @param encryptedData encryptedData
     * @param iv iv
     * @return phone number
     * @throws Exception e
     */
    public static PhoneNumberDTO decryptPhoneNumber(String sessionKey, String encryptedData, String iv) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String resultString = AESUtils.decrypt(encryptedData, sessionKey, iv);
        return objectMapper.readValue(resultString, PhoneNumberDTO.class);
    }

    /**
     * 获取手机号
     * @param sessionKey sessionKey
     * @param encryptedData encryptedData
     * @param iv iv
     * @return phone number
     * @throws Exception e
     */
    public static String decryptPhone(String sessionKey, String encryptedData, String iv) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String resultString = AESUtils.decrypt(encryptedData, sessionKey, iv);
        return objectMapper.readValue(resultString, PhoneNumberDTO.class).getPhoneNumber();
    }
}
