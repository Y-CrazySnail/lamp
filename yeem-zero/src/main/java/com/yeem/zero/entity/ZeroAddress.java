package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "zero_address", autoResultMap = true)
public class ZeroAddress extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 收货人
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 所在地区
     */
    private String area;
    /**
     * 详细地址
     */
    private String detail;
    /**
     * 默认标识 0否 1是
     */
    private Integer defaultFlag;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Integer defaultFlag) {
        this.defaultFlag = defaultFlag;
    }
}
