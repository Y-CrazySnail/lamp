package com.yeem.auth.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class VerificationCodeCache {
    private static final LoadingCache<String, String> VERIFICATION_CODE = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    return null;
                }
            });

    public static void put(String key, String value) {
        VERIFICATION_CODE.put(key, value);
    }

    public static String get(String key) {
        try {
            return VERIFICATION_CODE.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static void delete(String key) {
        VERIFICATION_CODE.invalidate(key);
    }
}
