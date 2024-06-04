package com.yeem.lamp.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.entity.Service;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "aladdin_package", autoResultMap = true)
public class PackageDo extends BaseEntity {
    private String type;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public Package convertPackage() {
        Package packages = new Package();
        packages.setType(this.type);
        packages.setDataTraffic(this.dataTraffic);
        packages.setPeriod(this.period);
        packages.setPrice(this.price);
        packages.setTitle(this.title);
        packages.setIntroduce(this.introduce);
        return packages;
    }
}
