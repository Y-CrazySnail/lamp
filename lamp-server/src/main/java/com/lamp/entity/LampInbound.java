package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import com.lamp.xui.model.XClientStat;
import com.lamp.xui.model.XInbound;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static List<LampInbound> batchConvert(List<XInbound> xInboundList) {
        if (Objects.isNull(xInboundList) || xInboundList.isEmpty()) {
            return null;
        }
        return xInboundList.stream().map(LampInbound::convert).collect(Collectors.toList());
    }

    public static LampInbound convert(XInbound xInbound) {
        LampInbound inbound = new LampInbound();
        inbound.setInboundId((long) xInbound.getId());
        inbound.setInboundPort(xInbound.getPort());
        inbound.setInboundProtocol(xInbound.getProtocol());
        inbound.setRemark(xInbound.getRemark());
        inbound.setSettings(xInbound.getSettings());
        inbound.setStreamSettings(xInbound.getStreamSettings());
        inbound.setSniffing(xInbound.getSniffing());
        List<XClientStat> xClientStatList = xInbound.getClientStats();
        List<LampClientTraffic> clientTrafficList = LampClientTraffic.batchConvert(xClientStatList);
        inbound.setClientTrafficList(clientTrafficList);
        return inbound;
    }
}
