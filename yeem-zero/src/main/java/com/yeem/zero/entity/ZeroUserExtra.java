package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@TableName(value = "zero_user_extra", autoResultMap = true)
public class ZeroUserExtra extends BaseEntity {
    /**
     * 直接推荐人-用户名
     */
    private Long directReferrerUserId;
    /**
     * 间接推荐人-用户名
     */
    private Long indirectReferrerUserId;
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
    /**
     * 微信登录Code
     */
    @TableField(exist = false)
    private String wechatLoginCode;
    /**
     * 微信登录响应session key
     */
    @TableField(exist = false)
    private String sessionKey;
    /**
     * 手机号密文
     */
    @TableField(exist = false)
    private String encryptedData;
    /**
     * 手机号IV
     */
    @TableField(exist = false)
    private String iv;
    /**
     * 授权token
     */
    @TableField(exist = false)
    private String token;
    /**
     * 地址列表
     */
    @TableField(exist = false)
    private List<ZeroAddress> zeroAddressList;

    public Long getDirectReferrerUserId() {
        return directReferrerUserId;
    }

    public void setDirectReferrerUserId(Long directReferrerUserId) {
        this.directReferrerUserId = directReferrerUserId;
    }

    public Long getIndirectReferrerUserId() {
        return indirectReferrerUserId;
    }

    public void setIndirectReferrerUserId(Long indirectReferrerUserId) {
        this.indirectReferrerUserId = indirectReferrerUserId;
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

    public String getWechatLoginCode() {
        return wechatLoginCode;
    }

    public void setWechatLoginCode(String wechatLoginCode) {
        this.wechatLoginCode = wechatLoginCode;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ZeroAddress> getZeroAddressList() {
        return zeroAddressList;
    }

    public void setZeroAddressList(List<ZeroAddress> zeroAddressList) {
        this.zeroAddressList = zeroAddressList;
    }
}
