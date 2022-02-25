package com.snail.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

@TableName(value = "demo_barber_shop", autoResultMap = true)
public class BarberShop extends BaseEntity {

    private String name;
    private String phone;
    private String city;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
