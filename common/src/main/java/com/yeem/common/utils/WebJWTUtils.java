package com.yeem.common.utils;

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
public class WebJWTUtils {

    private static final String KEY = "aladdin-lamp";

    public static String generateJWT(Long id) {
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
        payload.put("id", id);
        String token = JWTUtil.createToken(payload, KEY.getBytes());
        log.info("generate jwt. id:{}, expires:{}, token:{}", id, newTime, token);
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

    public static Long parseJWTId(String token) {
        if (!validate(token)) {
            return null;
        } else {
            JWT jwt = JWT.of(token);
            jwt.setKey(KEY.getBytes(StandardCharsets.UTF_8));
            return Long.valueOf(jwt.getPayload("id").toString());
        }
    }
}
