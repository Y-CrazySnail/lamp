package com.snail.utils;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.dto.PhoneNumberDTO;
import com.snail.dto.WxLoginResponse;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.util.Base64Utils;
import java.security.spec.AlgorithmParameterSpec;

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
    public static WxLoginResponse getWxLoginResponse(String appId, String appSecret, String code) throws IOException {
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
        byte[] encrypData = Base64Utils.decodeFromString(encryptedData);
        byte[] ivData = Base64Utils.decodeFromString(iv);
        byte[] sessionKeyByte = Base64Utils.decodeFromString(sessionKey);
        String resultString = null;
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
        SecretKeySpec keySpec = new SecretKeySpec(sessionKeyByte, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            resultString = new String(cipher.doFinal(encrypData), StandardCharsets.UTF_8);
        } catch (Exception e) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            resultString = new String(cipher.doFinal(encrypData), StandardCharsets.UTF_8);
        }
        return objectMapper.readValue(resultString, PhoneNumberDTO.class);
    }
}
