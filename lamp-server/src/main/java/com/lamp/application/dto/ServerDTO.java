package com.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import com.lamp.domain.entity.Server;
import lombok.Data;

@Data
public class ServerDTO {
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

    public ServerDTO(Server server) {
        BeanUtil.copyProperties(server, this);
    }

    public Server convertServer() {
        Server server = new Server();
        BeanUtil.copyProperties(this, server);
        return server;
    }
}
