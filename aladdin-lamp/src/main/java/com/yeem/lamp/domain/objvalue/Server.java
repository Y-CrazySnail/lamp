package com.yeem.lamp.domain.objvalue;

import lombok.Data;

@Data
public class Server {
    private Long id;
    private String apiIp;
    private int apiPort;
    private String apiUsername;
    private String apiPassword;
    private String nodeRemark;
    private int nodePort;
    private String subscribeIp;
    private int subscribePort;
    private String subscribeNamePrefix;
    private String postscript;
    private int sort;
    private int multiplyingPower;
}
