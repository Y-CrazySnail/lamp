package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_cart", autoResultMap = true)
@Data
public class OneCart extends BaseEntity {
    private Long tenantId;
    private Long storeId;
    private Long spuId;
    private Long skuId;
    private Long userId;
    private int quantity;
    @TableField(exist = false)
    private OneSpu spu;
    @TableField(exist = false)
    private OneSku sku;
}