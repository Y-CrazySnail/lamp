package com.lamp.infrastructure.xui.entity;

import lombok.Data;

@Data
public class XuiVmessSetting {
    private String email; // 对应ClientTraffic的email
    private boolean enable;
    private long expiryTime;
    private String id; // 对应uuid
    private int limitIp;
    private long reset;
    private String subId;
    private String tgId;
    private long totalGB;
}

