package com.yeem.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class PasswordUtil {
    public static void d() {
        String secretKey = "zero" + ":" + "123456";
        byte[] tokenBytes = secretKey.getBytes();
        String token64 = Base64.getEncoder().encodeToString(tokenBytes);
        String auth = "Basic " + token64;
        System.out.println(auth);
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        d();
    }
}
