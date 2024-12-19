package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import com.lamp.xui.entity.XuiInbound;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_inbound")
public class LampInbound extends BaseEntity {

    private Long serverId; // 服务器ID

    private Double multiplyingPower; // 倍率

    private Long xuiId; // xui入站ID

    private Integer xuiUserId;

    private Long xuiUp;

    private Long xuiDown;

    private Integer xuiTotal;

    private String xuiRemark;

    private int xuiEnable;

    private int xuiExpireTime;

    private int xuiListen;

    private int xuiPort;

    private String xuiProtocol;

    private String xuiSettings;

    private String xuiStreamSettings;

    private String xuiSniffing;

    private String xuiTag;

    @TableField(exist = false)
    private List<LampSubscription> subscriptionList; // 订阅列表

    @TableField(exist = false)
    private List<LampClientTraffic> clientTrafficList; // 客户端流量列表
}
