package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "his_patient")
public class HisPatient extends BaseEntity {
    private String patientName;
    private String patientPhone;
    private String patientDescription;
}
