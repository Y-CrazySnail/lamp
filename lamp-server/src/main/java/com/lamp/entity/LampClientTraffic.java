package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_client_traffic")
public class LampClientTraffic extends BaseEntity {

    private Long inboundId; // 入站ID

    private Long clientId; // xui客户ID

    private Boolean clientEnable; // xui客户启用状态 1启用 0关闭

    private String clientEmail; // xui客户email 对应inbound_id+_+service_month_id

    private Long clientUp; // xui客户上行流量

    private Long clientDown; // xui客户下行流量

    private Long expiryTime; // xui客户过期时间 0永不过期
}
