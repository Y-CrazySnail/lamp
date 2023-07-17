package com.snail.car_film_saas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

@TableName(value = "car_film_product_level")
public class CarFilmProductLevel extends BaseEntity {
    private String productNo; // 产品代码
    private String productLevelNo; // 产品级别代码
    private String productLevelName; // 产品级别名称
    private String channel; // 渠道
    private String status; // 状态 0下线 1上线

    public CarFilmProductLevel() {
    }

    public CarFilmProductLevel(String productNo, String productLevelNo, String productLevelName, String channel, String status) {
        this.productNo = productNo;
        this.productLevelNo = productLevelNo;
        this.productLevelName = productLevelName;
        this.channel = channel;
        this.status = status;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductLevelNo() {
        return productLevelNo;
    }

    public void setProductLevelNo(String productLevelNo) {
        this.productLevelNo = productLevelNo;
    }

    public String getProductLevelName() {
        return productLevelName;
    }

    public void setProductLevelName(String productLevelName) {
        this.productLevelName = productLevelName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
