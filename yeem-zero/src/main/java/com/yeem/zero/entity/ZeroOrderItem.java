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
    /**
     * 轮播图1
     */
    private String imageSwiperPath1;
    /**
     * 轮播图2
     */
    private String imageSwiperPath2;
    /**
     * 轮播图3
     */
    private String imageSwiperPath3;
    /**
     * 轮播图4
     */
    private String imageSwiperPath4;
    /**
     * 轮播图5
     */
    private String imageSwiperPath5;
    /**
     * 详情图1
     */
    private String imageDetailPath1;
    /**
     * 详情图2
     */
    private String imageDetailPath2;
    /**
     * 详情图3
     */
    private String imageDetailPath3;
    /**
     * 详情图4
     */
    private String imageDetailPath4;
    /**
     * 详情图5
     */
    private String imageDetailPath5;
    /**
     * 详情图6
     */
    private String imageDetailPath6;
    /**
     * 详情图7
     */
    private String imageDetailPath7;
    /**
     * 详情图8
     */
    private String imageDetailPath8;
    /**
     * 详情图9
     */
    private String imageDetailPath9;
    /**
     * 详情图10
     */
    private String imageDetailPath10;
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

    public String getImageSwiperPath1() {
        return imageSwiperPath1;
    }

    public void setImageSwiperPath1(String imageSwiperPath1) {
        this.imageSwiperPath1 = imageSwiperPath1;
    }

    public String getImageSwiperPath2() {
        return imageSwiperPath2;
    }

    public void setImageSwiperPath2(String imageSwiperPath2) {
        this.imageSwiperPath2 = imageSwiperPath2;
    }

    public String getImageSwiperPath3() {
        return imageSwiperPath3;
    }

    public void setImageSwiperPath3(String imageSwiperPath3) {
        this.imageSwiperPath3 = imageSwiperPath3;
    }

    public String getImageSwiperPath4() {
        return imageSwiperPath4;
    }

    public void setImageSwiperPath4(String imageSwiperPath4) {
        this.imageSwiperPath4 = imageSwiperPath4;
    }

    public String getImageSwiperPath5() {
        return imageSwiperPath5;
    }

    public void setImageSwiperPath5(String imageSwiperPath5) {
        this.imageSwiperPath5 = imageSwiperPath5;
    }

    public String getImageDetailPath1() {
        return imageDetailPath1;
    }

    public void setImageDetailPath1(String imageDetailPath1) {
        this.imageDetailPath1 = imageDetailPath1;
    }

    public String getImageDetailPath2() {
        return imageDetailPath2;
    }

    public void setImageDetailPath2(String imageDetailPath2) {
        this.imageDetailPath2 = imageDetailPath2;
    }

    public String getImageDetailPath3() {
        return imageDetailPath3;
    }

    public void setImageDetailPath3(String imageDetailPath3) {
        this.imageDetailPath3 = imageDetailPath3;
    }

    public String getImageDetailPath4() {
        return imageDetailPath4;
    }

    public void setImageDetailPath4(String imageDetailPath4) {
        this.imageDetailPath4 = imageDetailPath4;
    }

    public String getImageDetailPath5() {
        return imageDetailPath5;
    }

    public void setImageDetailPath5(String imageDetailPath5) {
        this.imageDetailPath5 = imageDetailPath5;
    }

    public String getImageDetailPath6() {
        return imageDetailPath6;
    }

    public void setImageDetailPath6(String imageDetailPath6) {
        this.imageDetailPath6 = imageDetailPath6;
    }

    public String getImageDetailPath7() {
        return imageDetailPath7;
    }

    public void setImageDetailPath7(String imageDetailPath7) {
        this.imageDetailPath7 = imageDetailPath7;
    }

    public String getImageDetailPath8() {
        return imageDetailPath8;
    }

    public void setImageDetailPath8(String imageDetailPath8) {
        this.imageDetailPath8 = imageDetailPath8;
    }

    public String getImageDetailPath9() {
        return imageDetailPath9;
    }

    public void setImageDetailPath9(String imageDetailPath9) {
        this.imageDetailPath9 = imageDetailPath9;
    }

    public String getImageDetailPath10() {
        return imageDetailPath10;
    }

    public void setImageDetailPath10(String imageDetailPath10) {
        this.imageDetailPath10 = imageDetailPath10;
    }

    public ZeroProduct getZeroProduct() {
        return zeroProduct;
    }

    public void setZeroProduct(ZeroProduct zeroProduct) {
        this.zeroProduct = zeroProduct;
    }
}
