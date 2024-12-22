package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import com.lamp.xui.entity.XuiClientTraffic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_client_traffic")
public class LampClientTraffic extends BaseEntity {

    private Long inboundId; // 入站ID
    private Long memberId; // 会员ID

    private Long xuiId; // xui客户ID

    private Long xuiInboundId;

    private Integer xuiEnable;

    private String xuiEmail;

    private Long xuiUp;

    private Long xuiDown;

    private Long xuiTotal;

    private Long xuiExpiryTime;

    private Integer xuiReset;

    public static LampClientTraffic convert(XuiClientTraffic xuiClientTraffic) {
        LampClientTraffic clientTraffic = new LampClientTraffic();
        String[] param = xuiClientTraffic.getEmail().split("_");
        Long inboundId = Long.valueOf(param[1]);
        Long memberId = Long.valueOf(param[3]);
        clientTraffic.setInboundId(inboundId);
        clientTraffic.setMemberId(memberId);
        clientTraffic.setXuiId(xuiClientTraffic.getId());
        clientTraffic.setXuiInboundId(xuiClientTraffic.getInboundId());
        clientTraffic.setXuiEnable(xuiClientTraffic.getEnable() ? 1 : 0);
        clientTraffic.setXuiEmail(xuiClientTraffic.getEmail());
        clientTraffic.setXuiUp(xuiClientTraffic.getUp());
        clientTraffic.setXuiDown(xuiClientTraffic.getDown());
        clientTraffic.setXuiTotal(xuiClientTraffic.getTotal());
        clientTraffic.setXuiExpiryTime(xuiClientTraffic.getExpiryTime());
        clientTraffic.setXuiReset(xuiClientTraffic.getReset());
        return clientTraffic;
    }
}
