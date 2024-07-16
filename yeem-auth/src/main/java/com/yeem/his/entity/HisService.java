package com.yeem.his.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "his_service")
public class HisService extends BaseEntity {
    private String serviceName;
    private String serviceDescription;
}
