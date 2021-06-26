package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName(value = "proxy_member", autoResultMap = true)
public class Member extends BaseEntity {

    private String uuid;
    private String wechat;
    private String qq;
    private Integer windows;
    private Integer mac;
    private Integer android;
    private Integer iphone;
    private Integer windowsNum;
    private Integer macNum;
    private Integer androidNum;
    private Integer iphoneNum;
    private String remark;
    private LocalDateTime end;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getWindows() {
        return windows;
    }

    public void setWindows(Integer windows) {
        this.windows = windows;
    }

    public Integer getMac() {
        return mac;
    }

    public void setMac(Integer mac) {
        this.mac = mac;
    }

    public Integer getAndroid() {
        return android;
    }

    public void setAndroid(Integer android) {
        this.android = android;
    }

    public Integer getIphone() {
        return iphone;
    }

    public void setIphone(Integer iphone) {
        this.iphone = iphone;
    }

    public Integer getWindowsNum() {
        return windowsNum;
    }

    public void setWindowsNum(Integer windowsNum) {
        this.windowsNum = windowsNum;
    }

    public Integer getMacNum() {
        return macNum;
    }

    public void setMacNum(Integer macNum) {
        this.macNum = macNum;
    }

    public Integer getAndroidNum() {
        return androidNum;
    }

    public void setAndroidNum(Integer androidNum) {
        this.androidNum = androidNum;
    }

    public Integer getIphoneNum() {
        return iphoneNum;
    }

    public void setIphoneNum(Integer iphoneNum) {
        this.iphoneNum = iphoneNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
