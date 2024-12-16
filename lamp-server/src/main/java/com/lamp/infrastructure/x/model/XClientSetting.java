package com.lamp.infrastructure.x.model;

import lombok.Data;

@Data
public class XClientSetting {
    /**
     * 对应service id
     */
    private String email;
    private String enable;
    private Long expiryTime;
    /**
     * 对应service uuid
     */
    private String id;
    private Integer limitIp;
    private Integer reset;
    private String subId;
    private String tgId;
    private Integer totalGB;
}