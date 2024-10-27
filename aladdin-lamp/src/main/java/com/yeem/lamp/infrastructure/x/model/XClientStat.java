package com.yeem.lamp.infrastructure.x.model;

import lombok.Data;

@Data
public class XClientStat {
    private int id;
    private int inboundId;
    private boolean enable;
    /**
     * 对应service id
     */
    private String email;
    private long up;
    private long down;
    private int expiryTime;
    private int total;
    private int reset;
}
