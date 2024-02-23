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
    private String wechatAppId;
    private String wechatAppSecret;
    private String belongUsername;
}
