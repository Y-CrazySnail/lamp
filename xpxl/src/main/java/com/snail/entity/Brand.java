package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "xpxl_brand", autoResultMap = true)
public class Brand extends BaseEntity {

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
