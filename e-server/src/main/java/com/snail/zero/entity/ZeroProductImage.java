package com.snail.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

@TableName(value = "zero_product_image", autoResultMap = true)
public class ZeroProductImage extends BaseEntity {
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
