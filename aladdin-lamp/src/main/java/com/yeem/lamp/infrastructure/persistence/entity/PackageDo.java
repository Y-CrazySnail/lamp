package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.objvalue.Plan;
import com.yeem.lamp.domain.objvalue.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_package", autoResultMap = true)
public class PackageDo extends BaseDo {
    private String type;
    private Integer bandwidth;
    private Integer period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public static PackageDo init(Product product) {
        PackageDo packageDo = new PackageDo();
        BeanUtil.copyProperties(product, packageDo);
        packageDo.setType(product.getProductType().getType());
        packageDo.setBandwidth(product.getPlan().getBandwidth());
        packageDo.setPeriod(product.getPlan().getPeriod());
        packageDo.setPrice(product.getPlan().getPrice());
        return packageDo;
    }

    public Product convertPackage() {
        Product product = new Product();
        BeanUtil.copyProperties(this, product);
        Plan plan = new Plan();
        plan.setBandwidth(this.bandwidth);
        plan.setPeriod(this.period);
        plan.setPrice(this.price);
        product.setPlan(plan);
        product.setProductType(ProductType.init(this.type));
        return product;
    }
}
