package com.yeem.lamp.infrastructure.x.model;

import lombok.Data;

@Data
public class XSystemStat {
    private double cpu;
    private int cpuCores;
    private double cpuSpeedMhz;
    private Memory mem;
    private Swap swap;
    private Disk disk;
    private Xray xray;
    private long uptime;
    private double[] loads;
    private int tcpCount;
    private int udpCount;
    private NetworkIO netIO;
    private NetworkTraffic netTraffic;
    private PublicIP publicIP;
    private AppStats appStats;

    // Getters and Setters

    @Data
    public static class Memory {
        private long current;
        private long total;
    }

    @Data
    public static class Swap {
        private long current;
        private long total;
    }

    @Data
    public static class Disk {
        private long current;
        private long total;
    }

    @Data
    public static class Xray {
        private String state;
        private String errorMsg;
        private String version;
    }

    @Data
    public static class NetworkIO {
        private long up;
        private long down;
    }

    @Data
    public static class NetworkTraffic {
        private long sent;
        private long recv;
    }

    @Data
    public static class PublicIP {
        private String ipv4;
        private String ipv6;
    }

    @Data
    public static class AppStats {
        private int threads;
        private long mem;
        private long uptime;
    }
}

