package com.snail.util;

import com.baomidou.mybatisplus.core.toolkit.AES;

public class EncryptorUtil {
    public static void main(String[] args) {
        String key = "cbb5165ec91c8361";
        System.out.println(key);
        String username = "tank";
        String password = "Tank20230620";
        System.out.println(AES.encrypt(username, key));
        System.out.println(AES.encrypt(password, key));
    }
}
