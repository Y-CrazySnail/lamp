package com.snail.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;


@TableName(value = "base_car_model")
public class CarModel extends BaseEntity {
    private String name;

    private String nameEn;

    private Long brandId;

    private String level;

    public CarModel(String name, String nameNe, Long brandId, String level) {
        this.name = name;
        this.nameEn = nameNe;
        this.brandId = brandId;
        this.level = level;
    }

    public CarModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameNe() {
        return nameEn;
    }

    public void setNameNe(String nameNe) {
        this.nameEn = nameNe;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
