package com.snail.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

@TableName(value = "demo_employee", autoResultMap = true)
public class Employee extends BaseEntity {

    private Long barbershopId;
    private String barbershopName;
    private String name;
    private String phone;
    private Integer barberPrice;

    public Long getBarbershopId() {
        return barbershopId;
    }

    public void setBarbershopId(Long barbershopId) {
        this.barbershopId = barbershopId;
    }

    public String getBarbershopName() {
        return barbershopName;
    }

    public void setBarbershopName(String barbershopName) {
        this.barbershopName = barbershopName;
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

    public Integer getBarberPrice() {
        return barberPrice;
    }

    public void setBarberPrice(Integer barberPrice) {
        this.barberPrice = barberPrice;
    }
}
