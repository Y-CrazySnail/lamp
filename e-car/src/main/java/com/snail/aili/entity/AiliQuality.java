package com.snail.aili.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

@TableName(value = "aili_quality", autoResultMap = true)
public class AiliQuality extends BaseEntity {
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
    private String workDateTime;
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

    public String getWorkDateTime() {
        return workDateTime;
    }

    public void setWorkDateTime(String workDateTime) {
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
}
