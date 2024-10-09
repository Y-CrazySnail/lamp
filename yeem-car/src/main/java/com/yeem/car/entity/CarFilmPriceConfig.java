package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "car_film_price_config")
public class CarFilmPriceConfig extends BaseEntity {
    private String productNo;
    private String filmType;
    private String body;
    private BigDecimal percent;
    private Integer sort;
    @TableField(exist = false)
    private BigDecimal price;
}
