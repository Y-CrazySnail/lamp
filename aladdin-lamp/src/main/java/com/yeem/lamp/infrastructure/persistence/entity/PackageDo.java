package com.yeem.lamp.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.objvalue.Plan;
import com.yeem.lamp.domain.objvalue.PlanType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_package", autoResultMap = true)
public class PackageDo extends BaseDo {
    private String type;
    private Integer dataTraffic;
    private Integer period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public static PackageDo init(Product product) {
        PackageDo packageDo = new PackageDo();
        packageDo.setId(product.getId());
        packageDo.setType(product.getPlan().getPlanType().getType());
        packageDo.setDataTraffic(product.getPlan().getBandwidth());
        packageDo.setPeriod(product.getPlan().getPeriod());
        packageDo.setPrice(product.getPlan().getPrice());
        packageDo.setTitle(product.getPlan().getTitle());
        packageDo.setIntroduce(product.getPlan().getIntroduce());
        return packageDo;
    }

    public Product convertPackage() {
        Product product = new Product();
        product.setId(this.getId());
        Plan plan = new Plan();
        plan.setBandwidth(this.dataTraffic);
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
