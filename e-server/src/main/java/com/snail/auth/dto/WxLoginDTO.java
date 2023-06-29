package com.snail.auth.dto;

import lombok.Data;

@Data
public class WxLoginDTO {
    /**
     * 微信Code
     */
    private String code;
    /**
     * 微信头像Url
     */
    private String avatarUrl;
    /**
     * 微信性别 0未知 1男 2女
     */
    private Integer gender;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 获取手机号Iv
     */
    private String iv;
    /**
     * 获取手机号加密信息
     */
    private String encryptedData;
}
