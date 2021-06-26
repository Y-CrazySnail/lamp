package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "mr_message", autoResultMap = true)
public class Message extends BaseEntity {
    private String name;
    private String phone;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}