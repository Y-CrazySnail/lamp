package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName(value = "proxy_member", autoResultMap = true)
public class Member extends BaseEntity {

    private String wechat;
    private String qq;
    private String remark;
    private LocalDateTime end;
    private Long trafficPerMonth;
    private Long trafficMonth;
    private Long trafficDownMonth;
    private Long trafficUpMonth;
    private Long trafficSurplusMonth;

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

    public Long getTrafficPerMonth() {
        return trafficPerMonth;
    }

    public void setTrafficPerMonth(Long trafficPerMonth) {
        this.trafficPerMonth = trafficPerMonth;
    }

    public Long getTrafficMonth() {
        return trafficMonth;
    }

    public void setTrafficMonth(Long trafficMonth) {
        this.trafficMonth = trafficMonth;
    }

    public Long getTrafficDownMonth() {
        return trafficDownMonth;
    }

    public void setTrafficDownMonth(Long trafficDownMonth) {
        this.trafficDownMonth = trafficDownMonth;
    }

    public Long getTrafficUpMonth() {
        return trafficUpMonth;
    }

    public void setTrafficUpMonth(Long trafficUpMonth) {
        this.trafficUpMonth = trafficUpMonth;
    }

    public Long getTrafficSurplusMonth() {
        return trafficSurplusMonth;
    }

    public void setTrafficSurplusMonth(Long trafficSurplusMonth) {
        this.trafficSurplusMonth = trafficSurplusMonth;
    }
}
