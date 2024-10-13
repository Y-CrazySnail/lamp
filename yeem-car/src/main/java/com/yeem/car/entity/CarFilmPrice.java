package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@TableName(value = "car_film_price")
public class CarFilmPrice extends BaseEntity {
    private String productNo;  // 产品代码
    private String productLevelNo;  // 产品级别代码
    /**
     * 汽车级别代码
     */
    private String carLevelNo;
    private BigDecimal price0;  // 整车
    private String priceKey1;
    private BigDecimal priceValue1;
    private String priceKey2;
    private BigDecimal priceValue2;
    private String priceKey3;
    private BigDecimal priceValue3;
    private String priceKey4;
    private BigDecimal priceValue4;
    private String priceKey5;
    private BigDecimal priceValue5;
    private String priceKey6;
    private BigDecimal priceValue6;
    private String priceKey7;
    private BigDecimal priceValue7;
    private String priceKey8;
    private BigDecimal priceValue8;
    private String priceKey9;
    private BigDecimal priceValue9;
    private String priceKey10;
    private BigDecimal priceValue10;
    private String priceKey11;
    private BigDecimal priceValue11;
    private String priceKey12;
    private BigDecimal priceValue12;
    private String priceKey13;
    private BigDecimal priceValue13;
    private String priceKey14;
    private BigDecimal priceValue14;
    private String priceKey15;
    private BigDecimal priceValue15;
    @TableField(exist = false)
    private List<CarFilmPriceConfig> carFilmPriceConfigList;
}
