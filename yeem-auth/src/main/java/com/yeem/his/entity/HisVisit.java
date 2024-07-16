package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "his_visit")
public class HisVisit extends BaseEntity {
    private Long patientId;
    private String patientName;
    private Long appointmentId;
    private Long serviceId;
    private String serviceName;
    private Long doctorId;
    private String doctorName;
    private Date visitTime;
    private BigDecimal visitCharge;
}
