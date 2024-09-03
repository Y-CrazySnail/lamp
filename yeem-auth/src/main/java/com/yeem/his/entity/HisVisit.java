package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long serviceId;
    private String serviceName;
    private Long doctorId;
    private String doctorName;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date appointmentTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone="GMT+8")
    private Date visitTime;
    private BigDecimal visitCharge;
    @TableField(exist = false)
    private HisPatient patient;
    @TableField(exist = false)
    private BigDecimal yearTotalCharge;
    @TableField(exist = false)
    private BigDecimal lastYearTotalCharge;
    @TableField(exist = false)
    private BigDecimal lastMonthTotalCharge;
    @TableField(exist = false)
    private BigDecimal monthTotalCharge;
}
