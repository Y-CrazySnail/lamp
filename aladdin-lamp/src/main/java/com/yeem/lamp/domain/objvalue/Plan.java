package com.yeem.lamp.domain.objvalue;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Plan {
    private PlanType planType;
    private BigDecimal price;
    private Integer bandwidth;
    private Integer period;
    private String title;
    private String introduce;
}
