package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 定价信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cf_price")
public class CFPrice extends BaseCF {
    /**
     * 产品代码
     */
    private String productNo;

    /**
     * 级别代码
     */
    private String levelNo;

    /**
     * 总价
     */
    private BigDecimal price;
    @TableField(exist = false)
    private List<CFPriceItem> cfPriceItemList;
}
