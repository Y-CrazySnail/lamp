package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_order_item", autoResultMap = true)
@Data
public class OneOrderItem extends BaseEntity {
    private Long orderId;
    private Long spuId;
    private String spuName;
    private String spuAttribute;
    private String spuShowImage;
    private Long skuId;
    private String skuName;
    private Integer skuPrice;
    private String skuAttribute;
    private String skuShowImage;
}
