package com.snail.auth.dto;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
