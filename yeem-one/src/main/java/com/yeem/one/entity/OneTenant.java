package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_tenant", autoResultMap = true)
@Data
public class OneTenant extends BaseEntity {
    private String tenantName;
    private String tenantPhone;
    private String tenantEmail;
    /**
     * 小程序信息
     */
    private String wechatAppId;
    private String wechatAppSecret;
    /**
     * 商户信息
     */
    private String wechatMerchantId;
    private String wechatPrivateKeyPath;
    private String wechatMerchantSerialNumber;
    private String wechatApiV3Key;
    /**
     * 归属管理账号
     */
    private String belongUsername;
}
