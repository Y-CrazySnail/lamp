package com.yeem.lamp.infrastructure.x.model;

import lombok.Data;

import java.util.List;

@Data
public class XInbound {
    private int id;
    private long up;
    private long down;
    private int total;
    private String remark;
    private boolean enable;
    private int expiryTime;
    private String listen;
    private int port;
    private String protocol;
    private String settings;
    private String streamSettings;
    private String tag;
    private String sniffing;
    private List<XClient> clientStats;
}
