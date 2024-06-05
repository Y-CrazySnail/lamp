package com.yeem.lamp.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.entity.Package;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_package", autoResultMap = true)
public class PackageDo extends BaseDo {
    private String type;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public PackageDo(Package packages) {
        this.setId(packages.getId());
        this.type = packages.getType();
        this.dataTraffic = packages.getDataTraffic();
        this.period = packages.getPeriod();
        this.price = packages.getPrice();
        this.title = packages.getTitle();
        this.introduce = packages.getIntroduce();
    }

    public Package convertPackage() {
        Package packages = new Package();
        packages.setId(this.getId());
        packages.setType(this.type);
        packages.setDataTraffic(this.dataTraffic);
        packages.setPeriod(this.period);
        packages.setPrice(this.price);
        packages.setTitle(this.title);
        packages.setIntroduce(this.introduce);
        return packages;
    }
}
