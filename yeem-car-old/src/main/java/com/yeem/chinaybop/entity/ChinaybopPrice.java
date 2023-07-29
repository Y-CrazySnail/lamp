package com.yeem.chinaybop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.entity.BaseEntity;

@TableName(value = "mr_price", autoResultMap = true)
public class ChinaybopPrice extends BaseEntity {
    /**
     * 车型
     */
    private String model;
    /**
     * 类型
     */
    private String type;
    /**
     * 级别
     */
    private String level;
    /**
     * 总价
     */
    private Integer price0;
    /**
     * 车顶盖
     */
    private Integer price1;
    /**
     * 引擎盖
     */
    private Integer price2;
    /**
     * 前保险杠
     */
    private Integer price3;
    /**
     * 后保险杠
     */
    private Integer price4;
    /**
     * 双前叶子板
     */
    private Integer price5;
    /**
     * 双后叶子板
     */
    private Integer price6;
    /**
     * 左门组合
     */
    private Integer price7;
    /**
     * 右门组合
     */
    private Integer price8;
    /**
     * 左侧裙
     */
    private Integer price9;
    /**
     * 右侧裙
     */
    private Integer price10;
    /**
     * 双后视镜
     */
    private Integer price11;
    /**
     * 后备箱盖
     */
    private Integer price12;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPrice0() {
        return price0;
    }

    public void setPrice0(Integer price0) {
        this.price0 = price0;
    }

    public Integer getPrice1() {
        return price1;
    }

    public void setPrice1(Integer price1) {
        this.price1 = price1;
    }

    public Integer getPrice2() {
        return price2;
    }

    public void setPrice2(Integer price2) {
        this.price2 = price2;
    }

    public Integer getPrice3() {
        return price3;
    }

    public void setPrice3(Integer price3) {
        this.price3 = price3;
    }

    public Integer getPrice4() {
        return price4;
    }

    public void setPrice4(Integer price4) {
        this.price4 = price4;
    }

    public Integer getPrice5() {
        return price5;
    }

    public void setPrice5(Integer price5) {
        this.price5 = price5;
    }

    public Integer getPrice6() {
        return price6;
    }

    public void setPrice6(Integer price6) {
        this.price6 = price6;
    }

    public Integer getPrice7() {
        return price7;
    }

    public void setPrice7(Integer price7) {
        this.price7 = price7;
    }

    public Integer getPrice8() {
        return price8;
    }

    public void setPrice8(Integer price8) {
        this.price8 = price8;
    }

    public Integer getPrice9() {
        return price9;
    }

    public void setPrice9(Integer price9) {
        this.price9 = price9;
    }

    public Integer getPrice10() {
        return price10;
    }

    public void setPrice10(Integer price10) {
        this.price10 = price10;
    }

    public Integer getPrice11() {
        return price11;
    }

    public void setPrice11(Integer price11) {
        this.price11 = price11;
    }

    public Integer getPrice12() {
        return price12;
    }

    public void setPrice12(Integer price12) {
        this.price12 = price12;
    }
}
