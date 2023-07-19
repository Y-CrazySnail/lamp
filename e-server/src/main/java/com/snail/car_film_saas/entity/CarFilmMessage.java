package com.snail.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.snail.entity.BaseEntity;

import java.util.Date;

@TableName(value = "car_film_message")
public class CarFilmMessage extends BaseEntity {

    private String productNo;  // 产品代码
    private String name;  // 姓名
    private String phone;  // 手机｜电话
    private String email;  // 邮箱
    private String content;  // 留言内容
    private Date datetime; // 留言时间
    private String sendStatus;  // 是否发送 0未发送 -1发送失败 1已发送
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendDatetime;  // 发送时间
    private String sendResult;  // 发送结果

    public CarFilmMessage() {
    }

    public CarFilmMessage(String productNo, String name, String phone, String email, String content, Date datetime, String sendStatus, Date sendDatetime, String sendResult) {
        this.productNo = productNo;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.content = content;
        this.datetime = datetime;
        this.sendStatus = sendStatus;
        this.sendDatetime = sendDatetime;
        this.sendResult = sendResult;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Date getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(Date sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }
}
