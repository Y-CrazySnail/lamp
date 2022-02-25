package com.snail.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

@TableName(value = "demo_member", autoResultMap = true)
public class Member extends BaseEntity {

    private String name;
    private String phone;

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
}
