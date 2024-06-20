package com.yeem.lamp.domain.entity;

import com.yeem.lamp.domain.objvalue.Subscription;
import lombok.Data;

import java.util.List;

@Data
public class Server {
    private Long id;
    private String apiIp;
    private int apiPort;
    private String apiUsername;
    private String apiPassword;
    private int inboundPort;
    private String region;
    private int multiplyingPower;
    private List<Subscription> subscriptionList;
}
