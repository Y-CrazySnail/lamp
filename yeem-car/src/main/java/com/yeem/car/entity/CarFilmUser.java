package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "car_film_user")
public class CarFilmUser extends BaseEntity {
    /**
     * 产品代码
     */
    private String productNo;
    /**
     * OpenID
     */
    private String openId;
    /**
     * Code
     */
    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String token;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 录入质保权限
     */
    private String qualityPermission;
    /**
     * 在保数量
     */
    @TableField(exist = false)
    private Integer normalQualityNumber;
    /**
     * 过期数量
     */
    @TableField(exist = false)
    private Integer expiredQualityNumber;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getQualityPermission() {
        return qualityPermission;
    }

    public void setQualityPermission(String qualityPermission) {
        this.qualityPermission = qualityPermission;
    }

    public Integer getNormalQualityNumber() {
        return normalQualityNumber;
    }

    public void setNormalQualityNumber(Integer normalQualityNumber) {
        this.normalQualityNumber = normalQualityNumber;
    }

    public Integer getExpiredQualityNumber() {
        return expiredQualityNumber;
    }

    public void setExpiredQualityNumber(Integer expiredQualityNumber) {
        this.expiredQualityNumber = expiredQualityNumber;
    }
}
