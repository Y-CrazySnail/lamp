package com.lamp.xui.model;

import lombok.Data;

@Data
public class XVmessClient {
    private String id;
    private String email;
    private int limitIp;
    private double totalGB;
    private long expiryTime;
    private boolean enable;
    private String tgId;
    private String subId;
    private int reset;

    public String getJson() {
        return "{\"id\":\"" + this.id + "\"," +
                "\"email\":\"" + this.email + "\"," +
                "\"limitIp\":" + this.limitIp + "," +
                "\"totalGB\":" + this.totalGB + "," +
                "\"expiryTime\":" + this.expiryTime + "," +
                "\"enable\":" + this.enable + "," +
                "\"tgId\":\"" + this.tgId + "\"," +
                "\"subId\":\"" + this.subId + "\"," +
                "\"reset\":" + this.reset + "}";
    }

    public XVmessClient(String uuid, Long serviceId) {
        this.id = uuid;
        this.email = String.valueOf(serviceId);
        this.limitIp = 0;
        this.totalGB = 0;
        this.expiryTime = 0;
        this.enable = true;
        this.tgId = "";
        this.subId = String.valueOf(serviceId);
        this.reset = 0;
    }
}
