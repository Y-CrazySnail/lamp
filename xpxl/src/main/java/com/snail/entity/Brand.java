package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "xpxl_brand", autoResultMap = true)
public class Brand extends BaseEntity {

    private String name;
    private String image;
    private Integer weight;

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
