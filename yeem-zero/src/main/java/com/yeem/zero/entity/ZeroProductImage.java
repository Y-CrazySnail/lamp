package com.yeem.zero.entity;

import com.yeem.common.entity.BaseEntity;

public class ZeroProductImage extends BaseEntity {

    public ZeroProductImage(Integer type, String path) {
        this.type = type;
        this.path = path;
    }

    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 图片类型 0展示图 1轮播图 2详情图
     */
    private Integer type;
    /**
     * 路径
     */
    private String path;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sort;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public enum Type {
        TYPE_SHOW(0),
        TYPE_SWIPER(1),
        TYPE_DETAIL(2);
        private final Integer type;

        Type(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }
    }
}
