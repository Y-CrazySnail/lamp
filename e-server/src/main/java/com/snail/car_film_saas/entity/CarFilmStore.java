package com.snail.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

@TableName(value = "car_film_store")
public class CarFilmStore extends BaseEntity {
    private String productNo; // 产品代码
    private String name; // 门店名称
    private String province; // 省
    private String city; // 市
    private String county; // 区
    private String address; // 详细地址
    private Double longitude; // 经度
    private Double latitude; // 纬度
    private String contactName; // 联系人姓名
    private String contactPhone; // 联系人手机号

    public CarFilmStore() {
    }

    public CarFilmStore(String productNo, String name, String province, String city, String county, String address, Double longitude, Double latitude, String contactName, String contactPhone) {
        this.productNo = productNo;
        this.name = name;
        this.province = province;
        this.city = city;
        this.county = county;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
