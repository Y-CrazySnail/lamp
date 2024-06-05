package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Services;
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
    private String period;
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
    }

    public Services convertService() {
        Services services = new Services();
        BeanUtil.copyProperties(this, services);
        return services;
    }
}
