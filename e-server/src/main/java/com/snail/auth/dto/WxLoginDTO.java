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
     * 微信性别
     */
    private String gender;
    /**
     * 微信昵称
     */
    private String nickName;
}
