package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_sku", autoResultMap = true)
@Data
public class OneSku extends BaseEntity {
    private Long tenantId;
    private Long spuId;
    private String skuName;
    private String skuAttribute;
    private Integer skuPrice;
    private Integer skuStock;
    private Integer skuSales;
    private Integer skuSort;
}