package com.snail.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@TableName(value = "zero_product", autoResultMap = true)
public class ZeroProduct extends BaseEntity {

    /**
     * 产品名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 销量
     */
    private Integer sale;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 展示图列表 type=0
     */
    @TableField(exist = false)
    private List<ZeroProductImage> zeroProductImageShowList;
    /**
     * 轮播图列表 type=1
     */
    @TableField(exist = false)
    private List<ZeroProductImage> zeroProductImageSwiperList;
    /**
     * 详情图列表 type=2
     */
    @TableField(exist = false)
    private List<ZeroProductImage> zeroProductImageDetailList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<ZeroProductImage> getZeroProductImageShowList() {
        return zeroProductImageShowList;
    }

    public void setZeroProductImageShowList(List<ZeroProductImage> zeroProductImageShowList) {
        this.zeroProductImageShowList = zeroProductImageShowList;
    }

    public List<ZeroProductImage> getZeroProductImageSwiperList() {
        return zeroProductImageSwiperList;
    }

    public void setZeroProductImageSwiperList(List<ZeroProductImage> zeroProductImageSwiperList) {
        this.zeroProductImageSwiperList = zeroProductImageSwiperList;
    }

    public List<ZeroProductImage> getZeroProductImageDetailList() {
        return zeroProductImageDetailList;
    }

    public void setZeroProductImageDetailList(List<ZeroProductImage> zeroProductImageDetailList) {
        this.zeroProductImageDetailList = zeroProductImageDetailList;
    }
}
