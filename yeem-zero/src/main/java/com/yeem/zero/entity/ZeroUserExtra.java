package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "zero_user_extra", autoResultMap = true)
public class ZeroUserExtra extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 直接推荐人-用户名
     */
    private String directReferrerUsername;
    /**
     * 间接推荐人-用户名
     */
    private String indirectReferrerUsername;
    /**
     * 分销标识 0否 1是
     */
    private Integer distributionFlag;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像链接
     */
    private String avatarUrl;
    /**
     * 性别 0未知 1男 2女
     */
    private Integer gender;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 微信OpenId
     */
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

    public String getIndirectReferrerUsername() {
        return indirectReferrerUsername;
    }

    public void setIndirectReferrerUsername(String indirectReferrerUsername) {
        this.indirectReferrerUsername = indirectReferrerUsername;
    }

    public String getDirectReferrerUsername() {
        return directReferrerUsername;
    }

    public void setDirectReferrerUsername(String directReferrerUsername) {
        this.directReferrerUsername = directReferrerUsername;
    }

    public Integer getDistributionFlag() {
        return distributionFlag;
    }

    public void setDistributionFlag(Integer distributionFlag) {
        this.distributionFlag = distributionFlag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }
}
