package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_car_brand")
public class BCBrand extends BaseEntity {

    private String name;

    private String nameEn;

    private String logoPath;

    @TableField(exist = false)
    private List<BaseCarModel> baseCarModelList;

    @TableField(exist = false)
    private Integer carModelCount;
}
