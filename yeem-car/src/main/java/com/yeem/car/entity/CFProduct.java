package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cf_product")
public class CFProduct extends BaseCF {
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 产品代码
     */
    private String productNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 状态 0线下 1线上
     */
    private String status;
    /**
     * 排序
     */
    private Integer sort;
}
