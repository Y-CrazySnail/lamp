package com.snail.util;

import com.baomidou.mybatisplus.core.toolkit.AES;

public class EncryptorUtil {
    public static void main(String[] args) {
//        auth:
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://ec2-54-223-205-20.cn-north-1.compute.amazonaws.com.cn:3306/auth_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
//        username: auth_dev
//        password: auth_dev
//        aili:
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://ec2-54-223-205-20.cn-north-1.compute.amazonaws.com.cn:3306/aili_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
//        username: aili_dev
//        password: aili_dev
//        chinaybop:
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://ec2-54-223-205-20.cn-north-1.compute.amazonaws.com.cn:3306/chinaybop_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
//        username: chinaybop_dev
//        password: chinaybop_dev
//        king:
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://ec2-54-223-205-20.cn-north-1.compute.amazonaws.com.cn:3306/king_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
//        username: king_dev
//        password: king_dev
//        proxy:
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://ec2-54-223-205-20.cn-north-1.compute.amazonaws.com.cn:3306/proxy_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
//        username: proxy_dev
//        password: proxy_dev
//        xpxl:
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://ec2-54-223-205-20.cn-north-1.compute.amazonaws.com.cn:3306/xpxl_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
//        username: xpxl_dev
//        password: xpxl_dev
//        tank:
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://ec2-54-223-205-20.cn-north-1.compute.amazonaws.com.cn:3306/tank_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
//        username: tank_dev
//        password: tank_dev
        String key = "cbb5165ec91c8361";
        System.out.println(key);
        String username = "tank";
        String password = "Tank20230620";
        System.out.println(AES.encrypt(username, key));
        System.out.println(AES.encrypt(password, key));
    }
}
