package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_store", autoResultMap = true)
@Data
public class OneStore extends BaseEntity {
    private Long tenantId;
    private String storeName;
    private String storePhone;
    private BigDecimal storeLongitude;
    private BigDecimal storeLatitude;
    private String storeAddress;
    private String storeLogo;
    private String storeDescription;
    private String storeWechat;
    private String storeWechatQrcode;
}
