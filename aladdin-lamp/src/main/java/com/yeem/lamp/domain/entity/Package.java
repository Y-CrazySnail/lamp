package com.yeem.lamp.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Package {
    private String type;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String title;
    private String introduce;
}

