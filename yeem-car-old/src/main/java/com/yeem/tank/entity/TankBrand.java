package com.yeem.tank.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "tank_brand", autoResultMap = true)
public class TankBrand extends BaseEntity {

    private String name;
    private String image;
    private String firstChar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }
}
