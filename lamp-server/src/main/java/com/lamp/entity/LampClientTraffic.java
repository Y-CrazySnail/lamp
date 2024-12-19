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
    private Long serviceMonthId; // 当月服务ID

    private Long xuiId; // xui客户ID

    private Long xuiInboundId;

    private Integer xuiEnable;

    private String xuiEmail;

    private Long xuiUp;

    private Long xuiDown;

    private Long xuiTotal;

    private Long xuiExpiryTime;

    private Integer xuiReset;

    public static List<LampClientTraffic> batchConvert(List<XClientStat> xClientStatList) {
        if (Objects.isNull(xClientStatList) || xClientStatList.isEmpty()) {
            return null;
        }
        return xClientStatList.stream().map(LampClientTraffic::convert).collect(Collectors.toList());
    }

    public static LampClientTraffic convert(XClientStat xClientStat) {
        LampClientTraffic clientTraffic = new LampClientTraffic();
//        clientTraffic.setClientId((long) xClientStat.getId());
//        clientTraffic.setClientEnable(xClientStat.isEnable());
//        clientTraffic.setClientEmail(xClientStat.getEmail());
//        clientTraffic.setClientUp(xClientStat.getUp());
//        clientTraffic.setClientDown(xClientStat.getDown());
//        clientTraffic.setExpiryTime((long) xClientStat.getExpiryTime());
        String[] split = xClientStat.getEmail().split("_");
        if (split.length == 2) {
            clientTraffic.setInboundId(Long.valueOf(xClientStat.getEmail().split("_")[0]));
            clientTraffic.setServiceMonthId(Long.valueOf(xClientStat.getEmail().split("_")[1]));
        }
        return clientTraffic;
    }
}
