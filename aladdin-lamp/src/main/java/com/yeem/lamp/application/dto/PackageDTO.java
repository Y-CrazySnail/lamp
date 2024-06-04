package com.yeem.lamp.application.dto;

import com.yeem.lamp.domain.entity.Package;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackageDTO {
    private String type;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String title;
    private String introduce;

    public PackageDTO(Package packages) {
        this.type = packages.getType();
        this.dataTraffic = packages.getDataTraffic();
        this.period = packages.getPeriod();
        this.price = packages.getPrice();
        this.title = packages.getTitle();
        this.introduce = packages.getIntroduce();
    }
}