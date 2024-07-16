package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "his_appointment")
public class HisAppointment extends BaseEntity {
    private Long patientId;
    private Long doctorId;
    private Long serviceId;
    private Date appointmentTime;
    private String appointmentStatus;
}
