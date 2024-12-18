package com.lamp.entity;

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
@TableName("lamp_client_traffic")
public class LampClientTraffic extends BaseEntity {

    private Long inboundId; // 入站ID
    private Long serviceMonthId;

    private Long clientId; // xui客户ID

    private Boolean clientEnable; // xui客户启用状态 1启用 0关闭

    private String clientEmail; // xui客户email 对应inbound_id+_+service_month_id

    private Long clientUp; // xui客户上行流量

    private Long clientDown; // xui客户下行流量

    private Long expiryTime; // xui客户过期时间 0永不过期

    public static List<LampClientTraffic> batchConvert(List<XClientStat> xClientStatList) {
        if (Objects.isNull(xClientStatList) || xClientStatList.isEmpty()) {
            return null;
        }
        return xClientStatList.stream().map(LampClientTraffic::convert).collect(Collectors.toList());
    }

    public static LampClientTraffic convert(XClientStat xClientStat) {
        LampClientTraffic clientTraffic = new LampClientTraffic();
        clientTraffic.setClientId((long) xClientStat.getId());
        clientTraffic.setClientEnable(xClientStat.isEnable());
        clientTraffic.setClientEmail(xClientStat.getEmail());
        clientTraffic.setClientUp(xClientStat.getUp());
        clientTraffic.setClientDown(xClientStat.getDown());
        clientTraffic.setExpiryTime((long) xClientStat.getExpiryTime());
        String[] split = xClientStat.getEmail().split("_");
        if (split.length == 2) {
            clientTraffic.setInboundId(Long.valueOf(xClientStat.getEmail().split("_")[0]));
            clientTraffic.setServiceMonthId(Long.valueOf(xClientStat.getEmail().split("_")[1]));
        }
        return clientTraffic;
    }
}
