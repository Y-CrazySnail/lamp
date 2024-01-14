package com.yeem.lamp.xui;

public class XUIVmessClient {
    private String id;
    private String email;
    private int totalGB;
    private long expiryTime;
    private boolean enable = true;
    private String tgId;
    private String subId;
    private int reset;

    public XUIVmessClient(String id, String email, int totalGB, long expiryTime, boolean enable, String tgId, String subId, int reset) {
        this.id = id;
        this.email = email;
        this.totalGB = totalGB;
        this.expiryTime = expiryTime;
        this.enable = enable;
        this.tgId = tgId;
        this.subId = subId;
        this.reset = reset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalGB() {
        return totalGB;
    }

    public void setTotalGB(int totalGB) {
        this.totalGB = totalGB;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getTgId() {
        return tgId;
    }

    public void setTgId(String tgId) {
        this.tgId = tgId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public int getReset() {
        return reset;
    }

    public void setReset(int reset) {
        this.reset = reset;
    }

    public String getString() {
        StringBuilder res = new StringBuilder();
        res.append("{\"id\":\"").append(this.id).append("\",");
        res.append("\"email\":\"").append(this.email).append("\",");
        res.append("\"totalGB\":").append(this.totalGB).append(",");
        res.append("\"expiryTime\":").append(this.expiryTime).append(",");
        res.append("\"enable\":").append(this.enable).append(",");
        res.append("\"tgId\":\"").append(this.tgId).append("\",");
        res.append("\"subId\":\"").append(this.subId).append("\",");
        res.append("\"reset\":").append(this.reset).append("}");
        return res.toString();
    }
}
