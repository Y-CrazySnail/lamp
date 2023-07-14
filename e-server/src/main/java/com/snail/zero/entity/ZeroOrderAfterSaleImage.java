package com.snail.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

@TableName(value = "zero_order_after_sale_image", autoResultMap = true)
public class ZeroOrderAfterSaleImage extends BaseEntity {
    private Long orderAfterSaleId;
    private String path;
    private String name;
    private String description;
    private Integer sort;

    public Long getOrderAfterSaleId() {
        return orderAfterSaleId;
    }

    public void setOrderAfterSaleId(Long orderAfterSaleId) {
        this.orderAfterSaleId = orderAfterSaleId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
