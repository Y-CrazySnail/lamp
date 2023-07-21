package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.entity.BaseEntity;

@TableName(value = "zero_cart", autoResultMap = true)
public class ZeroCart extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 数量
     */
    private int quantity;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
