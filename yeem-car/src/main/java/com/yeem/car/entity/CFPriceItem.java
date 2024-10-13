package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 部位定价信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cf_price_item")
public class CFPriceItem extends BaseCF {
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 部位
     */
    private String item;
    /**
     * 总价（百分比）
     */
    private BigDecimal percent;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 价格
     */
    @TableField(exist = false)
    private BigDecimal price;
}
