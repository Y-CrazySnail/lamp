package com.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.domain.objvalue.ServiceMonth;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_service_month", autoResultMap = true)
public class ServiceMonthDo extends BaseDo {
    private Long serviceId;
    private Integer serviceYear;
    private Integer serviceMonth;
    private Long bandwidth;
    private Long bandwidthUp;
    private Long bandwidthDown;

    public static ServiceMonthDo init(ServiceMonth serviceMonth) {
        ServiceMonthDo serviceMonthDo = new ServiceMonthDo();
        BeanUtil.copyProperties(serviceMonth, serviceMonthDo);
        return serviceMonthDo;
    }

    public ServiceMonth convertServiceMonth() {
        ServiceMonth serviceMonth = new ServiceMonth();
        BeanUtil.copyProperties(this, serviceMonth);
        return serviceMonth;
    }
}
