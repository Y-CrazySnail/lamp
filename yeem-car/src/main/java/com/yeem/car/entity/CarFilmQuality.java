package com.yeem.car.entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "car_film_quality")
public class CarFilmQuality extends BaseEntity {
    private String name; // 姓名
    private String phone; // 手机号
    private String qualityCardNo; // 质保卡号
    /**
     * 质保状态
     */
    @TableField(exist = false)
    private String state;
    private String plateNo; // 车牌号
    private String vin; // 车架号
    private String productNo; // 产品代码
    private String productName;// 产品名称
    private String productLevelNo; // 产品级别代码
    private String productLevelName; // 产品级别名称
    private BigDecimal price; // 价格
    private String carBrand; // 汽车品牌
    private String carModel; // 汽车型号
    private String carColor; // 汽车颜色
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workDate; // 施工时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validityDate; // 质保有效期
    private String workCompany; // 施工单位
    private String workStaff; // 施工技师
    private String workPart; // 施工部位
    private String rollNumber; // 卷心号
    private String boxNumber; // 盒头号
    private String approveFlag; //审核标识

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

    public String getQualityCardNo() {
        return qualityCardNo;
    }

    public void setQualityCardNo(String qualityCardNo) {
        this.qualityCardNo = qualityCardNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductLevelNo() {
        return productLevelNo;
    }

    public void setProductLevelNo(String productLevelNo) {
        this.productLevelNo = productLevelNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany;
    }

    public String getWorkStaff() {
        return workStaff;
    }

    public void setWorkStaff(String workStaff) {
        this.workStaff = workStaff;
    }

    public String getWorkPart() {
        return workPart;
    }

    public void setWorkPart(String workPart) {
        this.workPart = workPart;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLevelName() {
        return productLevelName;
    }

    public void setProductLevelName(String productLevelName) {
        this.productLevelName = productLevelName;
    }

    public String getApproveFlag() {
        return approveFlag;
    }

    public void setApproveFlag(String approveFlag) {
        this.approveFlag = approveFlag;
    }

    public enum State {
        NORMAL("1"),
        EXPIRED("0");
        private final String value;

        State(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void setState() {
        if (StringUtils.isEmpty(this.validityDate)) {
            this.state = State.EXPIRED.value;
        }
        if (DateUtil.compare(validityDate, new Date()) > 0) {
            this.state = State.NORMAL.value;
        } else {
            this.state = State.EXPIRED.value;
        }
    }
}
