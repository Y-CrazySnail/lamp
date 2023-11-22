package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
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
     * 标签 json格式
     */
    private String label;
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
     * 推荐标识
     */
    private Boolean recommendFlag;
    /**
     * 商品直接推荐费率 0未设置
     */
    private Integer directReferrerRate;
    /**
     * 商品间接推荐费率 0未设置
     */
    private Integer indirectReferrerRate;
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
    /**
     * 收藏标识 0否 1是
     */
    @TableField(exist = false)
    private String favoriteFlag;
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
    /**
     * 分类ID
     */
    private Integer categoryId;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public Boolean getRecommendFlag() {
        return recommendFlag;
    }

    public void setRecommendFlag(Boolean recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    public Integer getDirectReferrerRate() {
        return directReferrerRate;
    }

    public void setDirectReferrerRate(Integer directReferrerRate) {
        this.directReferrerRate = directReferrerRate;
    }

    public Integer getIndirectReferrerRate() {
        return indirectReferrerRate;
    }

    public void setIndirectReferrerRate(Integer indirectReferrerRate) {
        this.indirectReferrerRate = indirectReferrerRate;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getFavoriteFlag() {
        return favoriteFlag;
    }

    public void setFavoriteFlag(String favoriteFlag) {
        this.favoriteFlag = favoriteFlag;
    }

    public void dealProductImage(ZeroProduct zeroProduct) {
        // 展示图处理
        List<ZeroProductImage> zeroProductImageShowList = new ArrayList<>();
        if (!StringUtils.isEmpty(zeroProduct.getImageShowPath())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SHOW.getType(),
                    zeroProduct.getImageShowPath()
            );
            zeroProductImageShowList.add(zeroProductImage);
        }
        zeroProduct.setZeroProductImageShowList(zeroProductImageShowList);

        // 轮播图处理
        List<ZeroProductImage> zeroProductImageSwiperList = new ArrayList<>();
        if (!StringUtils.isEmpty(zeroProduct.getImageSwiperPath1())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageSwiperPath1()
            );
            zeroProductImageSwiperList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageSwiperPath2())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageSwiperPath2()
            );
            zeroProductImageSwiperList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageSwiperPath3())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageSwiperPath3()
            );
            zeroProductImageSwiperList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageSwiperPath4())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageSwiperPath4()
            );
            zeroProductImageSwiperList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageSwiperPath5())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageSwiperPath5()
            );
            zeroProductImageSwiperList.add(zeroProductImage);
        }
        zeroProduct.setZeroProductImageSwiperList(zeroProductImageSwiperList);

        // 详情图处理
        List<ZeroProductImage> zeroProductImageDetailList = new ArrayList<>();
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath1())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath1()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath2())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath2()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath3())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath3()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath4())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath4()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath5())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath5()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath6())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath6()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath7())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath7()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath8())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath8()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath9())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath9()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        if (!StringUtils.isEmpty(zeroProduct.getImageDetailPath10())) {
            ZeroProductImage zeroProductImage = new ZeroProductImage(
                    ZeroProductImage.Type.TYPE_SWIPER.getType(),
                    zeroProduct.getImageDetailPath10()
            );
            zeroProductImageDetailList.add(zeroProductImage);
        }
        zeroProduct.setZeroProductImageDetailList(zeroProductImageDetailList);
    }
}
