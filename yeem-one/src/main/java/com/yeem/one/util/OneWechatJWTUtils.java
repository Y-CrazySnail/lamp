package com.yeem.one.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OneWechatJWTUtils {

    private static final String KEY = "yeem-cloud";

    public static String generateJWT(Long tenantId, Long id, String openId) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.HOUR, 24);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 载荷
        payload.put("tenantId", tenantId);
        payload.put("id", id);
        payload.put("openId", openId);
        String token = JWTUtil.createToken(payload, KEY.getBytes());
        log.info("generate jwt. tenantId:{}, openId:{}, expires:{}, token:{}", tenantId, openId, newTime, token);
        return token;
    }

    public static boolean validate(String token) {
        JWT jwt = JWT.of(token);
        jwt.setKey(KEY.getBytes(StandardCharsets.UTF_8));
        jwt.setExpiresAt(DateTime.now().offsetNew(DateField.HOUR, 24));
        if (jwt.validate(0)) {
            return jwt.validate(0);
        } else {
            throw new RuntimeException("token error");
        }
    }

    public static Long parseJWTTenantId(String token) {
        if (!validate(token)) {
            return null;
        } else {
            JWT jwt = JWT.of(token);
            jwt.setKey(KEY.getBytes(StandardCharsets.UTF_8));
            return Long.valueOf(jwt.getPayload("tenantId").toString());
        }
    }

    public static Long parseJWTId(String token) {
        if (!validate(token)) {
            return null;
        } else {
            JWT jwt = JWT.of(token);
            jwt.setKey(KEY.getBytes(StandardCharsets.UTF_8));
            return Long.valueOf(jwt.getPayload("id").toString());
        }
    }

    public static String parseJWTOpenId(String token) {
        if (!validate(token)) {
            return null;
        } else {
            JWT jwt = JWT.of(token);
            jwt.setKey(KEY.getBytes(StandardCharsets.UTF_8));
            return String.valueOf(jwt.getPayload("openId").toString());
        }
    }

    public static void main(String[] args) {
        System.out.println(generateJWT(1L, 12345678L, "openIdTest"));
    }
}
