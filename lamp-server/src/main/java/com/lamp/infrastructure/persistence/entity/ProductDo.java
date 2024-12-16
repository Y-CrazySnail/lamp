package com.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.domain.entity.Product;
import com.lamp.domain.objvalue.Plan;
import com.lamp.domain.objvalue.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_product", autoResultMap = true)
public class ProductDo extends BaseDo {
    private String type;
    private Integer bandwidth;
    private Integer period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public static ProductDo init(Product product) {
        ProductDo productDo = new ProductDo();
        BeanUtil.copyProperties(product, productDo);
        productDo.setType(product.getProductType().getType());
        productDo.setBandwidth(product.getPlan().getBandwidth());
        productDo.setPeriod(product.getPlan().getPeriod());
        productDo.setPrice(product.getPlan().getPrice());
        return productDo;
    }

    public Product convertProduct() {
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
