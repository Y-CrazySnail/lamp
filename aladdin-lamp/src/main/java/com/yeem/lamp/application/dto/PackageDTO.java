package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.objvalue.Plan;
import com.yeem.lamp.domain.objvalue.ProductType;
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
        this.type = product.getProductType().getType();
        this.dataTraffic = product.getPlan().getBandwidth();
        this.period = product.getPlan().getPeriod();
        this.price = product.getPlan().getPrice();
        this.title = product.getTitle();
        this.introduce = product.getIntroduce();
    }

    public Product convertPackage() {
        Product product = new Product();
        BeanUtil.copyProperties(this, product);
        Plan plan = new Plan();
        plan.setBandwidth(this.dataTraffic);
        plan.setPeriod(this.period);
        plan.setPrice(this.price);
        product.setPlan(plan);
        product.setProductType(ProductType.init(this.type));
        return product;
    }
}