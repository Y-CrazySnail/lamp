package com.yeem.lamp.application.dto;

import com.yeem.lamp.domain.entity.Package;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackageDTO {
    private Long id;
    private String type;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public PackageDTO(Package packages) {
        this.id = packages.getId();
        this.type = packages.getType();
        this.dataTraffic = packages.getDataTraffic();
        this.period = packages.getPeriod();
        this.price = packages.getPrice();
        this.title = packages.getTitle();
        this.introduce = packages.getIntroduce();
    }

    public Package convertPackage() {
        Package packages = new Package();
        packages.setId(this.id);
        packages.setType(this.type);
        packages.setDataTraffic(this.dataTraffic);
        packages.setPeriod(this.period);
        packages.setPrice(this.price);
        packages.setTitle(this.title);
        packages.setIntroduce(this.introduce);
        return packages;
    }
}