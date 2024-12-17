package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_inbound")
public class LampInbound extends BaseEntity {

    private Long serverId; // 服务器ID

    private Double multiplyingPower; // 倍率

    private Long inboundId; // xui入站ID

    private Integer inboundPort; // xui入站端口

    private String inboundProtocol; // xui入站协议

    private String remark; // xui入站备注

    private String settings; // xui入站设置

    private String streamSettings; // xui入站流设置

    private String sniffing; // xui入站监听

    @TableField(exist = false)
    private List<LampSubscription> subscriptionList; // 订阅列表

    @TableField(exist = false)
    private List<LampClientTraffic> clientTrafficList; // 客户端流量列表
}
