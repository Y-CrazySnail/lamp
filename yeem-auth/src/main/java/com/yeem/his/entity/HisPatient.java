package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "his_patient")
public class HisPatient extends BaseEntity {
    private String patientName;
    private String patientPhone;
    private String patientIdNo;
    private String patientSex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date patientBirthday;
    private String patientDescription;
}
