package com.yeem.car.entity;

import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseCF extends BaseEntity {
    private String tenantNo;
}
