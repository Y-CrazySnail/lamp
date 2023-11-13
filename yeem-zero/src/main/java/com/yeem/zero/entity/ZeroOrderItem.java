package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;

@TableName(value = "zero_order_item", autoResultMap = true)
public class ZeroOrderItem extends BaseEntity {
    private Long orderId;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal amount;
    /**
     * 展示图
     */
    private String imageShowPath;
    @TableField(exist = false)
    private ZeroProduct zeroProduct;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getImageShowPath() {
        return imageShowPath;
    }

    public void setImageShowPath(String imageShowPath) {
        this.imageShowPath = imageShowPath;
    }

    public ZeroProduct getZeroProduct() {
        return zeroProduct;
    }

    public void setZeroProduct(ZeroProduct zeroProduct) {
        this.zeroProduct = zeroProduct;
    }
}
