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
}
密码优酷5-7天 账号密码登录，只能固定一台设备使用 禁止多登！否则会冻结封号！请遵守使用！（登陆账号点击邮箱验证， 然后打开接码地址查询验证码！）接码地址http://129.211.15.21:5663/?username=wq98872@163ya.cn