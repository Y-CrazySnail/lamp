package com.yeem.lamp.application.dto;

import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.objvalue.Plan;
import com.yeem.lamp.domain.objvalue.PlanType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackageDTO {
    private Long id;
    private String type;
    private Integer dataTraffic;
    private Integer period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public PackageDTO(Product product) {
        this.id = product.getId();
        this.type = product.getPlan().getPlanType().getType();
        this.dataTraffic = product.getPlan().getDataTraffic();
        this.period = product.getPlan().getPeriod();
        this.price = product.getPlan().getPrice();
        this.title = product.getPlan().getTitle();
        this.introduce = product.getPlan().getIntroduce();
    }

    public Product convertPackage() {
        Product product = new Product();
        product.setId(this.id);
        Plan plan = new Plan();
        plan.setDataTraffic(this.dataTraffic);
        plan.setPeriod(this.period);
        plan.setPrice(this.price);
        plan.setTitle(this.title);
        plan.setIntroduce(this.introduce);
        PlanType planType = PlanType.init(this.type);
        plan.setPlanType(planType);
        product.setPlan(plan);
        return product;
    }
}