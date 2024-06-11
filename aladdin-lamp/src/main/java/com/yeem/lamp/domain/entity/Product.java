package com.yeem.lamp.domain.entity;

import com.yeem.lamp.domain.objvalue.Plan;
import lombok.Data;

@Data
public class Product {
    private Long id;
    Plan plan;
}
