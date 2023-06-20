package com.snail.util;

import com.baomidou.mybatisplus.core.toolkit.AES;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class EncryptorUtil {
    public static void main(String[] args) {
        String randomKey = AES.generateRandomKey();
        String data2 = "jdbc:mysql://42.192.164.50:3306/star?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
        String result = AES.encrypt(data2, "8fd7029c559f8b03");
        System.out.println("8fd7029c559f8b03"+"||"+result);
    }
}
