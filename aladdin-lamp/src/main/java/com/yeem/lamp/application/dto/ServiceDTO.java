package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private Integer bandwidth;
    private Integer period;
    private BigDecimal price;
    private String uuid;

    private ServiceMonth currentServiceMonth;
    private List<ServiceMonth> serviceMonthList;
    private List<ServiceRecord> serviceRecordList;
    private List<Subscription> subscriptionList;
    private List<NodeVmess> nodeVmessList;

    /**
     * 0未生效 1已生效 9已过期
     */
    private String status;
    private String wechat;
    private String email;
    private Double serviceUp;
    private Double serviceDown;
    private String surplus;

    public static ServiceDTO init(Services services) {
        ServiceDTO serviceDTO = new ServiceDTO();
        BeanUtil.copyProperties(services, serviceDTO);
        serviceDTO.setBandwidth(services.getPlan().getBandwidth());
        serviceDTO.setPeriod(services.getPlan().getPeriod());
        serviceDTO.setPrice(services.getPlan().getPrice());
        serviceDTO.setCurrentServiceMonth(services.getCurrentServiceMonth());
        serviceDTO.setServiceMonthList(services.getServiceMonthList());
        serviceDTO.setServiceRecordList(services.getServiceRecordList());
        return serviceDTO;
    }

    public Services convertService() {
        Services services = new Services();
        BeanUtil.copyProperties(this, services);
        Plan plan = new Plan();
        plan.setPeriod(this.period);
        plan.setBandwidth(this.bandwidth);
        plan.setPrice(this.price);
        services.setPlan(plan);
        return services;
    }
}
