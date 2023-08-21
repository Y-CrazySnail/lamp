package com.yeem.common.dto;

public class WechatMiniProgramDTO {
    private Long userId;
    private String username;
    private String referrerUsername;
    /**
     * 应用
     */
    private String application;
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
    private String phoneNumber;
    private String response;
    private String wechatOpenId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReferrerUsername() {
        return referrerUsername;
    }

    public void setReferrerUsername(String referrerUsername) {
        this.referrerUsername = referrerUsername;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }
}
