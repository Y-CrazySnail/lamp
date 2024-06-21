package com.yeem.lamp.domain.objvalue;

import lombok.Data;

@Data
public class ProductType {

    private static final String PRODUCT_TYPE_REGULAR = "regular";
    private static final String PRODUCT_TYPE_DATA = "data";

    /**
     * regular 常规服务
     * data 流量包
     */
    private String type;

    public static ProductType init(String type) {
        if (PRODUCT_TYPE_REGULAR.equals(type)) {
            return regularPlanType();
        }
        if (PRODUCT_TYPE_DATA.equals(type)) {
            return dataPlanType();
        }
        return null;
    }

    public static ProductType regularPlanType() {
        ProductType planType = new ProductType();
        planType.setType(PRODUCT_TYPE_REGULAR);
        return planType;
    }

    public static ProductType dataPlanType() {
        ProductType planType = new ProductType();
        planType.setType(PRODUCT_TYPE_DATA);
        return planType;
    }

    public boolean isRegular() {
        return PRODUCT_TYPE_REGULAR.equals(this.type);
    }

    public boolean isData() {
        return PRODUCT_TYPE_DATA.equals(this.type);
    }
}
