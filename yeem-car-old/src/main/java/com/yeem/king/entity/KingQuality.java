package com.yeem.king.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.time.LocalDateTime;

@TableName(value = "king_quality", autoResultMap = true)
public class KingQuality extends BaseEntity {
    /**
     * 客户姓名
     */
    private String name;
    /**
     * 客户手机号
     */
    private String phone;
    /**
     * 汽车型号
     */
    private String carModel;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 车架号
     */
    private String carShelfNumber;
    /**
     * 汽车颜色
     */
    private String carColor;
    /**
     * 施工时间
     */
    private LocalDateTime workDateTime;
    /**
     * 施工单位
     */
    private String workCompany;
    /**
     * 施工部位
     */
    private String workPart;
    /**
     * 施工技师
     */
    private String workStaff;
    /**
     * 产品级别
     */
    private String productLevel;

    private String qualityCardId;

    private String rollNumber;

    private String boxNumber;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarShelfNumber() {
        return carShelfNumber;
    }

    public void setCarShelfNumber(String carShelfNumber) {
        this.carShelfNumber = carShelfNumber;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public LocalDateTime getWorkDateTime() {
        return workDateTime;
    }

    public void setWorkDateTime(LocalDateTime workDateTime) {
        this.workDateTime = workDateTime;
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany;
    }

    public String getWorkPart() {
        return workPart;
    }

    public void setWorkPart(String workPart) {
        this.workPart = workPart;
    }

    public String getWorkStaff() {
        return workStaff;
    }

    public void setWorkStaff(String workStaff) {
        this.workStaff = workStaff;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public String getQualityCardId() {
        return qualityCardId;
    }

    public void setQualityCardId(String qualityCardId) {
        this.qualityCardId = qualityCardId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }
}
