package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_subscription")
public class LampSubscription extends BaseEntity {

    private Long inboundId; // 入站ID

    private String host; // 地址

    private Integer port; // 端口

    private String name; // 订阅名称

    private Integer sort; // 排序 从小到大

    private String regionEmoji; // 地区图标

    private String regionName; // 地区名称

    private String code; // 编码

    private String isp; // 运营商

    private String feature; // 特性
}
