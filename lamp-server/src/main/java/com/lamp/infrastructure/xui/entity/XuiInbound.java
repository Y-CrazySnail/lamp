package com.lamp.infrastructure.xui.entity;

import lombok.Data;

import java.util.List;

@Data
public class XuiInbound {
    private long id;
    private long up;
    private long down;
    private int total;
    private String remark;
    private boolean enable;
    private long expiryTime;
    private String listen;
    private int port;
    private String protocol;
    private String tag;
    private String settings;
    private XuiSettings settingsObject;
    private String streamSettings;
    private String sniffing;
    private List<XuiClientTraffic> clientStats;
}
