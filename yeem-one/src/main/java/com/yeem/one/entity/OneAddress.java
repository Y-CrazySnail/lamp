package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_address", autoResultMap = true)
@Data
public class OneAddress extends BaseEntity {
    private Long tenantId;
    private Long userId;
    private String addressName;
    private String addressPhone;
    private String addressProvince;
    private String addressCity;
    private String addressDistrict;
    private String addressStreet;
    private String addressDetail;
    private boolean addressDefaultFlag;
}