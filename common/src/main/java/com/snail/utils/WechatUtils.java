package com.snail.utils;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.dto.PhoneNumberDTO;
import com.snail.dto.WxLoginResponse;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
        byte[] sessionKeyBytes = Base64.getDecoder().decode(sessionKey);
        byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
        byte[] ivBytes = Base64.getDecoder().decode(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(sessionKeyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedDataBytes = cipher.doFinal(encryptedDataBytes);
        String phoneNumberStr = new String(decryptedDataBytes, StandardCharsets.UTF_8);
        return objectMapper.readValue(phoneNumberStr, PhoneNumberDTO.class);
    }
}
