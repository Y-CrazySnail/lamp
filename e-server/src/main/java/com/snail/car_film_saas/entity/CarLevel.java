package com.snail.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

@TableName(value = "base_car_level")
public class CarLevel extends BaseEntity {
    /**
     * 级别代码
     */
    private String levelNo;
    /**
     * 级别名称
     */
    private String levelName;

    public CarLevel() {
    }

    public CarLevel(String levelNo, String levelName) {
        this.levelNo = levelNo;
        this.levelName = levelName;
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
