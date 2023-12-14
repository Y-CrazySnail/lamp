package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.util.List;

@TableName(value = "car_film_product")
public class CarFilmProduct extends BaseEntity {
    private String productNo; // 产品代码
    private String productType; // 产品代码
    private String productLevelNo; // 产品级别代码
    private String productLevelName; // 产品级别名称
    private String channel; // 渠道
    private String status; // 状态 0下线 1上线
    @TableField(exist = false)
    private List<CarFilmPrice> carFilmPriceList;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public List<CarFilmPrice> getCarFilmPriceList() {
        return carFilmPriceList;
    }

    public void setCarFilmPriceList(List<CarFilmPrice> carFilmPriceList) {
        this.carFilmPriceList = carFilmPriceList;
    }
}
