package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.Plan;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_service", autoResultMap = true)
public class ServiceDo extends BaseDo {
    private Long memberId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private Integer bandwidth;
    private Integer period;
    private BigDecimal price;
    private String uuid;
    /**
     * 最后同步时间
     */
    private Date lastSyncTime;

    public static ServiceDo init(Services services) {
        ServiceDo serviceDo = new ServiceDo();
        BeanUtil.copyProperties(services, serviceDo);
        serviceDo.setBandwidth(services.getPlan().getBandwidth());
        serviceDo.setPeriod(services.getPlan().getPeriod());
        serviceDo.setPrice(services.getPlan().getPrice());
        return serviceDo;
    }

    public Services convertService() {
        Services services = new Services();
        BeanUtil.copyProperties(this, services);
        Plan plan = new Plan();
        plan.setBandwidth(this.bandwidth);
        plan.setPrice(this.price);
        plan.setPeriod(this.period);
        services.setPlan(plan);
        return services;
    }

}
