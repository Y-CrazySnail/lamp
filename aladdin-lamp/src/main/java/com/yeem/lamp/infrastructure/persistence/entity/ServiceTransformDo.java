package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.objvalue.Plan;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.objvalue.ServiceTransform;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_service_record", autoResultMap = true)
public class ServiceTransformDo extends BaseDo {
    private Long serviceId;
    private Integer originBandwidth;
    private Integer originPeriod;
    private BigDecimal originPrice;
    private Date originEndDate;
    private Integer targetBandwidth;
    private Integer targetPeriod;
    private BigDecimal targetPrice;
    private Date targetEndDate;
    private Date transformDate;

    public static ServiceTransformDo init(ServiceTransform serviceTransform) {
        ServiceTransformDo serviceTransformDo = new ServiceTransformDo();
        BeanUtil.copyProperties(serviceTransform, serviceTransformDo);
        serviceTransformDo.setOriginBandwidth(serviceTransform.getOriginPlan().getBandwidth());
        serviceTransformDo.setOriginPeriod(serviceTransform.getOriginPlan().getPeriod());
        serviceTransformDo.setOriginPrice(serviceTransform.getOriginPlan().getPrice());
        serviceTransformDo.setTargetBandwidth(serviceTransform.getTargetPlan().getBandwidth());
        serviceTransformDo.setTargetPeriod(serviceTransform.getTargetPlan().getPeriod());
        serviceTransformDo.setTargetPrice(serviceTransform.getTargetPlan().getPrice());
        return serviceTransformDo;
    }

    public ServiceRecord convertServiceTransform() {
        ServiceTransform serviceTransform = new ServiceTransform();
        BeanUtil.copyProperties(this, serviceTransform);
        Plan originPlan = new Plan();
        originPlan.setBandwidth(this.originBandwidth);
        originPlan.setPeriod(this.originPeriod);
        originPlan.setPrice(this.originPrice);
        Plan targetPlan = new Plan();
        return null;
    }
}
