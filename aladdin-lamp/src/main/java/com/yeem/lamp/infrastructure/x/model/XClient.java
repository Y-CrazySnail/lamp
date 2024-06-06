package com.yeem.lamp.infrastructure.x.model;

import lombok.Data;

@Data
public class XClient {
    private int id;
    private int inboundId;
    private boolean enable;
    private String email;
    private long up;
    private long down;
    private int expiryTime;
    private int total;
    private int reset;
}
