package com.yeem.lamp.xui;

import java.util.List;

public class XUIInboundData {
    private int id;
    private long up;
    private long down;
    private int total;
    private String remark;
    private boolean enable;
    private long expiryTime;
    private List<ClientStats> clientStats;
    private String listen;
    private int port;
    private String protocol;
    private String settings;
    private String streamSettings;
    private String tag;
    private String sniffing;

    public static class ClientStats {
        private int id;
        private int inboundId;
        private boolean enable;
        private String email;
        private long up;
        private long down;
        private long expiryTime;
        private int total;
        private int reset;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInboundId() {
            return inboundId;
        }

        public void setInboundId(int inboundId) {
            this.inboundId = inboundId;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getUp() {
            return up;
        }

        public void setUp(long up) {
            this.up = up;
        }

        public long getDown() {
            return down;
        }

        public void setDown(long down) {
            this.down = down;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(long expiryTime) {
            this.expiryTime = expiryTime;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getReset() {
            return reset;
        }

        public void setReset(int reset) {
            this.reset = reset;
        }
    }

    public static class Client {
        private String email;
        private boolean enable;
        private long expiryTime;
        private String id;
        private int reset;
        private String subId;
        private String tgId;
        private double totalGB;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(long expiryTime) {
            this.expiryTime = expiryTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getReset() {
            return reset;
        }

        public void setReset(int reset) {
            this.reset = reset;
        }

        public String getSubId() {
            return subId;
        }

        public void setSubId(String subId) {
            this.subId = subId;
        }

        public String getTgId() {
            return tgId;
        }

        public void setTgId(String tgId) {
            this.tgId = tgId;
        }

        public double getTotalGB() {
            return totalGB;
        }

        public void setTotalGB(double totalGB) {
            this.totalGB = totalGB;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUp() {
        return up;
    }

    public void setUp(long up) {
        this.up = up;
    }

    public long getDown() {
        return down;
    }

    public void setDown(long down) {
        this.down = down;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public List<ClientStats> getClientStats() {
        return clientStats;
    }

    public void setClientStats(List<ClientStats> clientStats) {
        this.clientStats = clientStats;
    }

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getStreamSettings() {
        return streamSettings;
    }

    public void setStreamSettings(String streamSettings) {
        this.streamSettings = streamSettings;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSniffing() {
        return sniffing;
    }

    public void setSniffing(String sniffing) {
        this.sniffing = sniffing;
    }
}
