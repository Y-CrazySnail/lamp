package com.snail.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

import java.util.Date;

@TableName(value = "car_film_product")
public class CarFilmProduct extends BaseEntity {
    private String productNo;  // 产品代码
    private String productName;  // 产品名称
    private Date publishDate;  // 产品成立日期
    private String operationPrice;  // 产品运营维护价格
    private String companyName;  // 企业名称
    private String companyNo;  // 企业营业执照-统一社会信用代码
    private String managerName;  // 管理人姓名
    private String managerPhone;  // 管理人手机号
    private String managerEmail;  // 管理人邮箱
    private String miniProgramFlag;  // 开通小程序 0否1是
    private String miniProgramName;  // 微信小程序名称
    private String officialWebsiteFlag;  // 官方网站 0否1是
    private String officialWebsiteDomain;  // 官方网站域名}
}
