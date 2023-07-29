package com.yeem.tank.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "tank_model", autoResultMap = true)
public class TankModel extends BaseEntity {
    private String name;
    private String type;
    private String image;
    private Long brandId;
    private String brandName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
