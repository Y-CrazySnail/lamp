package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_service_record", autoResultMap = true)
public class ServiceRecordDo extends BaseDo {
    private Long serviceId;
    private Long serviceMonthId;
    private Date serviceDate;
    private Long bandwidthUp;
    private Long bandwidthDown;
    private String region;

    public static ServiceRecordDo init(ServiceRecord serviceRecord) {
        ServiceRecordDo serviceRecordDo = new ServiceRecordDo();
        BeanUtil.copyProperties(serviceRecord, serviceRecordDo);
        return serviceRecordDo;
    }

    public ServiceRecord convertServiceRecord() {
        ServiceRecord serviceRecord = new ServiceRecord();
        BeanUtil.copyProperties(this, serviceRecord);
        return serviceRecord;
    }
}
