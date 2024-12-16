package com.lamp.domain.objvalue;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Plan {
    private Integer bandwidth;
    private Integer period;
    private BigDecimal price;
}
