package com.yeem.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "car_film_technician")
public class CarFilmTechnician extends BaseEntity {
    private String productNo; // 产品代码
    private String name; // 技师姓名
    private String province; // 省
    private String city; // 市
    private String county; // 区
    private String level; // 技师级别

    public CarFilmTechnician() {
    }

    public CarFilmTechnician(String productNo, String name, String province, String city, String county, String level) {
        this.productNo = productNo;
        this.name = name;
        this.province = province;
        this.city = city;
        this.county = county;
        this.level = level;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
