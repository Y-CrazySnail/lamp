package com.yeem.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.util.List;

@TableName(value = "aladdin_member", autoResultMap = true)
public class AladdinMember extends BaseEntity {
    private String email;
    private String wechat;
    private String uuid;
    private String remark;
    private String password;
    @TableField(exist = false)
    private List<AladdinService> serviceList;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AladdinService> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<AladdinService> serviceList) {
        this.serviceList = serviceList;
    }
}
