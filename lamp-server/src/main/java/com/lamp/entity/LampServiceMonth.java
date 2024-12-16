package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_service_month")
public class LampServiceMonth extends BaseEntity {
    private Long serviceId; // 服务ID

    private Integer serviceYear; // 年份

    private Integer serviceMonth; // 月份

    private Long bandwidth; // 流量

    private Long bandwidthUp; // 上行流量

    private Long bandwidthDown; // 下行流量
}
