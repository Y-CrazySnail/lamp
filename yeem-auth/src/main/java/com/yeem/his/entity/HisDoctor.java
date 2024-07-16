package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "his_doctor")
public class HisDoctor extends BaseEntity {
    private String doctorName;
    private String doctorPhone;
    private String doctorLevel;
}
