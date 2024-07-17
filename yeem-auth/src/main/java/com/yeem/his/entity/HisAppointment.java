package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(exist = false)
    private String patientName;
    private Long doctorId;
    @TableField(exist = false)
    private String doctorName;
    private Long serviceId;
    @TableField(exist = false)
    private String serviceName;
    private Date appointmentTime;
    private String appointmentStatus;
}
