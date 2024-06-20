package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.Plan;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ServiceDTO {
    private Long id;
    private Long memberId;
    /**
     * 类型 0周期服务 1数据加量包
     */
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private Integer dataTraffic;
    private Integer period;
    private BigDecimal price;
    private String uuid;
    /**
     * 0未生效 1已生效 9已过期
     */
    private String status;
    private String wechat;
    private String email;
    private Double serviceUp;
    private Double serviceDown;
    private String surplus;

    public ServiceDTO(Services services) {
        BeanUtil.copyProperties(services, this);
        this.dataTraffic = services.getPlan().getBandwidth();
        this.period = services.getPlan().getPeriod();
        this.price = services.getPlan().getPrice();
    }

    public Services convertService() {
        Services services = new Services();
        BeanUtil.copyProperties(this, services);
        Plan plan = new Plan();
        plan.setPeriod(this.period);
        plan.setBandwidth(this.dataTraffic);
        plan.setPrice(this.price);
        services.setPlan(plan);
        return services;
    }
}
