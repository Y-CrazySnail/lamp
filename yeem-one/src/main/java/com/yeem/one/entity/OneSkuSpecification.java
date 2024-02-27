package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_sku_specification", autoResultMap = true)
@Data
public class OneSkuSpecification extends BaseEntity {
    private Long tenantId;
    private Long skuId;
    private String specificationKey;
    private String specificationValue;
    private Integer specificationSort;
}