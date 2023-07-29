package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.util.List;

@TableName(value = "zero_category", autoResultMap = true)
public class ZeroCategory extends BaseEntity {

    /**
     * 类别名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 产品列表
     */
    @TableField(exist = false)
    private List<ZeroProduct> zeroProductList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<ZeroProduct> getZeroProductList() {
        return zeroProductList;
    }

    public void setZeroProductList(List<ZeroProduct> zeroProductList) {
        this.zeroProductList = zeroProductList;
    }
}
