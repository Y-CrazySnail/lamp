package com.snail.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;


@TableName(value = "base_car_model")
public class CarModel extends BaseEntity {
    private String name;

    private String nameEn;

    private Long brandId;

    private String levelNo;

    @TableField(exist = false)
    private String levelName;

    public CarModel() {
    }

    public CarModel(String name, String nameEn, Long brandId, String levelNo, String levelName) {
        this.name = name;
        this.nameEn = nameEn;
        this.brandId = brandId;
        this.levelNo = levelNo;
        this.levelName = levelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
