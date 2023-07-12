package com.snail.car_film_saas.entity;

import com.snail.entity.BaseEntity;

import java.util.Date;


public class CarFilmProduct extends BaseEntity {
    /**
     * 产品代码
     */
    private String productNo;

    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品成立日期
     */

    private Date publishDate;

    public CarFilmProduct(String productNo, String productName, Date publishDate) {
        this.productNo = productNo;
        this.productName = productName;
        this.publishDate = publishDate;
    }

    public CarFilmProduct() {
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
