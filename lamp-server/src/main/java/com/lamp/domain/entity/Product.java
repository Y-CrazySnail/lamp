package com.lamp.domain.entity;

import com.lamp.domain.objvalue.Plan;
import com.lamp.domain.objvalue.ProductType;
import lombok.Data;

@Data
public class Product {
    private Long id;
    private String title;
    private String introduce;
    private ProductType productType;
    private Plan plan;
}
