package com.lamp.infrastructure.xui.entity;

import lombok.Data;

@Data
public class XuiClientTraffic {

    private Long id;

    private Long inboundId;

    private Boolean enable;

    private String email;

    private Long up;

    private Long down;

    private Long total;

    private Long expiryTime;

    private Integer reset;
}
