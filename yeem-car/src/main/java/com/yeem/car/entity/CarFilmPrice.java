package com.yeem.car.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;
@TableName(value = "car_film_price")
public class CarFilmPrice extends BaseEntity {
    private String productNo;  // 产品代码
    private String productLevelNo;  // 产品级别代码
    private String carLevelNo;  // 汽车级别代码
    private BigDecimal price0;  // 整车
    private String priceKey1;
    private BigDecimal priceValue1;
    private String priceKey2;
    private BigDecimal priceValue2;
    private String priceKey3;
    private BigDecimal priceValue3;
    private String priceKey4;
    private BigDecimal priceValue4;
    private String priceKey5;
    private BigDecimal priceValue5;
    private String priceKey6;
    private BigDecimal priceValue6;
    private String priceKey7;
    private BigDecimal priceValue7;
    private String priceKey8;
    private BigDecimal priceValue8;
    private String priceKey9;
    private BigDecimal priceValue9;
    private String priceKey10;
    private BigDecimal priceValue10;
    private String priceKey11;
    private BigDecimal priceValue11;
    private String priceKey12;
    private BigDecimal priceValue12;
    private String priceKey13;
    private BigDecimal priceValue13;
    private String priceKey14;
    private BigDecimal priceValue14;
    private String priceKey15;
    private BigDecimal priceValue15;

    public CarFilmPrice() {
    }

    public CarFilmPrice(String productNo, String productLevelNo, String carLevelNo, BigDecimal price0, String priceKey1, BigDecimal priceValue1, String priceKey2, BigDecimal priceValue2, String priceKey3, BigDecimal priceValue3, String priceKey4, BigDecimal priceValue4, String priceKey5, BigDecimal priceValue5, String priceKey6, BigDecimal priceValue6, String priceKey7, BigDecimal priceValue7, String priceKey8, BigDecimal priceValue8, String priceKey9, BigDecimal priceValue9, String priceKey10, BigDecimal priceValue10, String priceKey11, BigDecimal priceValue11, String priceKey12, BigDecimal priceValue12, String priceKey13, BigDecimal priceValue13, String priceKey14, BigDecimal priceValue14, String priceKey15, BigDecimal priceValue15) {
        this.productNo = productNo;
        this.productLevelNo = productLevelNo;
        this.carLevelNo = carLevelNo;
        this.price0 = price0;
        this.priceKey1 = priceKey1;
        this.priceValue1 = priceValue1;
        this.priceKey2 = priceKey2;
        this.priceValue2 = priceValue2;
        this.priceKey3 = priceKey3;
        this.priceValue3 = priceValue3;
        this.priceKey4 = priceKey4;
        this.priceValue4 = priceValue4;
        this.priceKey5 = priceKey5;
        this.priceValue5 = priceValue5;
        this.priceKey6 = priceKey6;
        this.priceValue6 = priceValue6;
        this.priceKey7 = priceKey7;
        this.priceValue7 = priceValue7;
        this.priceKey8 = priceKey8;
        this.priceValue8 = priceValue8;
        this.priceKey9 = priceKey9;
        this.priceValue9 = priceValue9;
        this.priceKey10 = priceKey10;
        this.priceValue10 = priceValue10;
        this.priceKey11 = priceKey11;
        this.priceValue11 = priceValue11;
        this.priceKey12 = priceKey12;
        this.priceValue12 = priceValue12;
        this.priceKey13 = priceKey13;
        this.priceValue13 = priceValue13;
        this.priceKey14 = priceKey14;
        this.priceValue14 = priceValue14;
        this.priceKey15 = priceKey15;
        this.priceValue15 = priceValue15;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductLevelNo() {
        return productLevelNo;
    }

    public void setProductLevelNo(String productLevelNo) {
        this.productLevelNo = productLevelNo;
    }

    public String getCarLevelNo() {
        return carLevelNo;
    }

    public void setCarLevelNo(String carLevelNo) {
        this.carLevelNo = carLevelNo;
    }

    public BigDecimal getPrice0() {
        return price0;
    }

    public void setPrice0(BigDecimal price0) {
        this.price0 = price0;
    }

    public String getPriceKey1() {
        return priceKey1;
    }

    public void setPriceKey1(String priceKey1) {
        this.priceKey1 = priceKey1;
    }

    public BigDecimal getPriceValue1() {
        return priceValue1;
    }

    public void setPriceValue1(BigDecimal priceValue1) {
        this.priceValue1 = priceValue1;
    }

    public String getPriceKey2() {
        return priceKey2;
    }

    public void setPriceKey2(String priceKey2) {
        this.priceKey2 = priceKey2;
    }

    public BigDecimal getPriceValue2() {
        return priceValue2;
    }

    public void setPriceValue2(BigDecimal priceValue2) {
        this.priceValue2 = priceValue2;
    }

    public String getPriceKey3() {
        return priceKey3;
    }

    public void setPriceKey3(String priceKey3) {
        this.priceKey3 = priceKey3;
    }

    public BigDecimal getPriceValue3() {
        return priceValue3;
    }

    public void setPriceValue3(BigDecimal priceValue3) {
        this.priceValue3 = priceValue3;
    }

    public String getPriceKey4() {
        return priceKey4;
    }

    public void setPriceKey4(String priceKey4) {
        this.priceKey4 = priceKey4;
    }

    public BigDecimal getPriceValue4() {
        return priceValue4;
    }

    public void setPriceValue4(BigDecimal priceValue4) {
        this.priceValue4 = priceValue4;
    }

    public String getPriceKey5() {
        return priceKey5;
    }

    public void setPriceKey5(String priceKey5) {
        this.priceKey5 = priceKey5;
    }

    public BigDecimal getPriceValue5() {
        return priceValue5;
    }

    public void setPriceValue5(BigDecimal priceValue5) {
        this.priceValue5 = priceValue5;
    }

    public String getPriceKey6() {
        return priceKey6;
    }

    public void setPriceKey6(String priceKey6) {
        this.priceKey6 = priceKey6;
    }

    public BigDecimal getPriceValue6() {
        return priceValue6;
    }

    public void setPriceValue6(BigDecimal priceValue6) {
        this.priceValue6 = priceValue6;
    }

    public String getPriceKey7() {
        return priceKey7;
    }

    public void setPriceKey7(String priceKey7) {
        this.priceKey7 = priceKey7;
    }

    public BigDecimal getPriceValue7() {
        return priceValue7;
    }

    public void setPriceValue7(BigDecimal priceValue7) {
        this.priceValue7 = priceValue7;
    }

    public String getPriceKey8() {
        return priceKey8;
    }

    public void setPriceKey8(String priceKey8) {
        this.priceKey8 = priceKey8;
    }

    public BigDecimal getPriceValue8() {
        return priceValue8;
    }

    public void setPriceValue8(BigDecimal priceValue8) {
        this.priceValue8 = priceValue8;
    }

    public String getPriceKey9() {
        return priceKey9;
    }

    public void setPriceKey9(String priceKey9) {
        this.priceKey9 = priceKey9;
    }

    public BigDecimal getPriceValue9() {
        return priceValue9;
    }

    public void setPriceValue9(BigDecimal priceValue9) {
        this.priceValue9 = priceValue9;
    }

    public String getPriceKey10() {
        return priceKey10;
    }

    public void setPriceKey10(String priceKey10) {
        this.priceKey10 = priceKey10;
    }

    public BigDecimal getPriceValue10() {
        return priceValue10;
    }

    public void setPriceValue10(BigDecimal priceValue10) {
        this.priceValue10 = priceValue10;
    }

    public String getPriceKey11() {
        return priceKey11;
    }

    public void setPriceKey11(String priceKey11) {
        this.priceKey11 = priceKey11;
    }

    public BigDecimal getPriceValue11() {
        return priceValue11;
    }

    public void setPriceValue11(BigDecimal priceValue11) {
        this.priceValue11 = priceValue11;
    }

    public String getPriceKey12() {
        return priceKey12;
    }

    public void setPriceKey12(String priceKey12) {
        this.priceKey12 = priceKey12;
    }

    public BigDecimal getPriceValue12() {
        return priceValue12;
    }

    public void setPriceValue12(BigDecimal priceValue12) {
        this.priceValue12 = priceValue12;
    }

    public String getPriceKey13() {
        return priceKey13;
    }

    public void setPriceKey13(String priceKey13) {
        this.priceKey13 = priceKey13;
    }

    public BigDecimal getPriceValue13() {
        return priceValue13;
    }

    public void setPriceValue13(BigDecimal priceValue13) {
        this.priceValue13 = priceValue13;
    }

    public String getPriceKey14() {
        return priceKey14;
    }

    public void setPriceKey14(String priceKey14) {
        this.priceKey14 = priceKey14;
    }

    public BigDecimal getPriceValue14() {
        return priceValue14;
    }

    public void setPriceValue14(BigDecimal priceValue14) {
        this.priceValue14 = priceValue14;
    }

    public String getPriceKey15() {
        return priceKey15;
    }

    public void setPriceKey15(String priceKey15) {
        this.priceKey15 = priceKey15;
    }

    public BigDecimal getPriceValue15() {
        return priceValue15;
    }

    public void setPriceValue15(BigDecimal priceValue15) {
        this.priceValue15 = priceValue15;
    }
}
