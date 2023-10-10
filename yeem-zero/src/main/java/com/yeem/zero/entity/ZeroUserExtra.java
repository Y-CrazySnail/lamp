package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;

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
     * 直接分销佣金比例
     */
    private Integer directReferrerRate;
    /**
     * 间接分销佣金比例
     */
    private Integer indirectReferrerRate;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 待结算金额
     */
    private BigDecimal todoBalance;
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
    /**
     * 推荐用户数量
     */
    @TableField(exist = false)
    private Integer referrerUserCount;
    /**
     * 推荐订单数量
     */
    @TableField(exist = false)
    private Integer referrerOrderCount;

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

    public Integer getDirectReferrerRate() {
        return directReferrerRate;
    }

    public void setDirectReferrerRate(Integer directReferrerRate) {
        this.directReferrerRate = directReferrerRate;
    }

    public Integer getIndirectReferrerRate() {
        return indirectReferrerRate;
    }

    public void setIndirectReferrerRate(Integer indirectReferrerRate) {
        this.indirectReferrerRate = indirectReferrerRate;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTodoBalance() {
        return todoBalance;
    }

    public void setTodoBalance(BigDecimal todoBalance) {
        this.todoBalance = todoBalance;
    }

    public Integer getReferrerUserCount() {
        return referrerUserCount;
    }

    public void setReferrerUserCount(Integer referrerUserCount) {
        this.referrerUserCount = referrerUserCount;
    }

    public Integer getReferrerOrderCount() {
        return referrerOrderCount;
    }

    public void setReferrerOrderCount(Integer referrerOrderCount) {
        this.referrerOrderCount = referrerOrderCount;
    }
}
