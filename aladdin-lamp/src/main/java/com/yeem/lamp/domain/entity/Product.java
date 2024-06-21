package com.yeem.lamp.domain.entity;

import com.yeem.lamp.domain.objvalue.Plan;
import com.yeem.lamp.domain.objvalue.ProductType;
import lombok.Data;

@Data
public class Product {
    private Long id;
    private String title;
    private String introduce;
    private ProductType productType;
    private Plan plan;
}
