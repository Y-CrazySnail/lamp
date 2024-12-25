package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_product")
public class LampProduct extends BaseEntity {
    private String type;
    private String memberLevel;
    private Long bandwidth;
    private Integer period;
    private BigDecimal price;
    private BigDecimal beforePrice;
    private String title;
    private String introduce;
}
