package com.yeem.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;

import java.util.Date;

@TableName(value = "car_film_tenant")
public class CarFilmTenant extends BaseEntity {
    private String productNo;  // 产品代码
    private String productName;  // 产品名称
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date publishDate;  // 产品成立日期
    private String operationPrice;  // 产品运营维护价格
    private String companyName;  // 企业名称
    private String companyNo;  // 企业营业执照-统一社会信用代码
    private String authorizedUsername; // 授权用户名
    private String managerName;  // 管理人姓名
    private String managerPhone;  // 管理人手机号
    private String managerEmail;  // 管理人邮箱
    private String miniProgramFlag;  // 开通小程序 0否1是
    private String miniProgramName;  // 微信小程序名称
    private String officialWebsiteFlag;  // 官方网站 0否1是
    private String officialWebsiteDomain;  // 官方网站域名}

    public CarFilmTenant() {
    }

    public CarFilmTenant(String productNo, String productName, Date publishDate, String operationPrice, String companyName, String companyNo, String managerName, String managerPhone, String managerEmail, String miniProgramFlag, String miniProgramName, String officialWebsiteFlag, String officialWebsiteDomain) {
        this.productNo = productNo;
        this.productName = productName;
        this.publishDate = publishDate;
        this.operationPrice = operationPrice;
        this.companyName = companyName;
        this.companyNo = companyNo;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.managerEmail = managerEmail;
        this.miniProgramFlag = miniProgramFlag;
        this.miniProgramName = miniProgramName;
        this.officialWebsiteFlag = officialWebsiteFlag;
        this.officialWebsiteDomain = officialWebsiteDomain;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getOperationPrice() {
        return operationPrice;
    }

    public void setOperationPrice(String operationPrice) {
        this.operationPrice = operationPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getMiniProgramFlag() {
        return miniProgramFlag;
    }

    public void setMiniProgramFlag(String miniProgramFlag) {
        this.miniProgramFlag = miniProgramFlag;
    }

    public String getMiniProgramName() {
        return miniProgramName;
    }

    public void setMiniProgramName(String miniProgramName) {
        this.miniProgramName = miniProgramName;
    }

    public String getOfficialWebsiteFlag() {
        return officialWebsiteFlag;
    }

    public void setOfficialWebsiteFlag(String officialWebsiteFlag) {
        this.officialWebsiteFlag = officialWebsiteFlag;
    }

    public String getOfficialWebsiteDomain() {
        return officialWebsiteDomain;
    }

    public void setOfficialWebsiteDomain(String officialWebsiteDomain) {
        this.officialWebsiteDomain = officialWebsiteDomain;
    }

    public String getAuthorizedUsername() {
        return authorizedUsername;
    }

    public void setAuthorizedUsername(String authorizedUsername) {
        this.authorizedUsername = authorizedUsername;
    }
}
