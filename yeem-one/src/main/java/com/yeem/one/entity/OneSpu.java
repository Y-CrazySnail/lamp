package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_spu", autoResultMap = true)
@Data
public class OneSpu extends BaseEntity {
    private Long tenantId;
    private Long storeId;
    @TableField(exist = false)
    private String storeName;
    private Long categoryId;
    @TableField(exist = false)
    private String categoryName;
    private String spuName;
    private String spuAttribute;
    private String spuShowImage;
    private String spuSwiperImage1;
    private String spuSwiperImage2;
    private String spuSwiperImage3;
    private String spuDetailImage1;
    private String spuDetailImage2;
    private String spuDetailImage3;
    private String spuDetailImage4;
    private String spuDetailImage5;
    private String spuDetailImage6;
    private Integer spuSort;
}