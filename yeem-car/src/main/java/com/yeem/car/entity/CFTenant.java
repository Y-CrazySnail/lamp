package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 租户信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cf_tenant")
public class CFTenant extends BaseCF {
    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户成立日期
     */
    private Date publishDate;

    /**
     * 产品运营维护价格
     */
    private String operationPrice;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业营业执照-统一社会信用代码
     */
    private String companyNo;

    /**
     * 授权用户名
     */
    private String authorizedUsername;

    /**
     * 管理人姓名
     */
    private String managerName;

    /**
     * 管理人手机号
     */
    private String managerPhone;

    /**
     * 管理人邮箱
     */
    private String managerEmail;

    /**
     * 开通小程序 0否1是
     */
    private String miniProgramFlag;

    /**
     * 微信小程序名称
     */
    private String miniProgramName;

    /**
     * 官方网站 0否1是
     */
    private String officialWebsiteFlag;

    /**
     * 官方网站域名
     */
    private String officialWebsiteDomain;
    /**
     * 产品列表
     */
    @TableField(exist = false)
    private List<CFProduct> productList;
}