package com.yeem.lamp.domain.objvalue;

import lombok.Data;

@Data
public class PlanType {
    /**
     * regular 常规服务
     * data 流量包
     */
    private String type;

    public static PlanType init(String type) {
        if ("regular".equals(type)) {
            return regularPlanType();
        } else if ("data".equals(type)) {
            return dataPlanType();
        } else {
            throw new RuntimeException("plan type error");
        }
    }

    public static PlanType regularPlanType() {
        PlanType planType = new PlanType();
        planType.setType("regular");
        return planType;
    }

    public static PlanType dataPlanType() {
        PlanType planType = new PlanType();
        planType.setType("data");
        return planType;
    }
}
