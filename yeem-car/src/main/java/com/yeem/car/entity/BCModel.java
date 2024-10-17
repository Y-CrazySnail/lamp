package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_car_model")
public class BCModel extends BaseEntity {
    private String name;

    private String nameEn;

    private Long brandId;

    private String levelNo;

    @TableField(exist = false)
    private String levelName;
}
