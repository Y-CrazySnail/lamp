package com.yeem.lamp.infrastructure.x.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
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
    private List<XClientStat> clientStats;
    private List<XClientSetting> clientSettings;

    public void initClientSettings() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<XClientSetting>> clients = objectMapper.readValue(this.settings.replace("\n", ""), new TypeReference<Map<String, List<XClientSetting>>>() {
            });
            this.clientSettings = clients.get("clients");
        } catch (IOException e) {
            log.error("setting反序列化异常");
        }
    }

    public void initSteamSettings() {
        this.streamSettings = "{\n" +
                "  \"network\": \"tcp\",\n" +
                "  \"security\": \"none\",\n" +
                "  \"externalProxy\": [],\n" +
                "  \"tcpSettings\": {\n" +
                "    \"acceptProxyProtocol\": false,\n" +
                "    \"header\": {\n" +
                "      \"type\": \"none\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public void initSniffing() {
        this.sniffing = "{\n" +
                "  \"enabled\": true,\n" +
                "  \"destOverride\": [\n" +
                "    \"http\",\n" +
                "    \"tls\",\n" +
                "    \"quic\",\n" +
                "    \"fakedns\"\n" +
                "  ],\n" +
                "  \"metadataOnly\": false,\n" +
                "  \"routeOnly\": false" +
                "}";
    }

    public void initVmessSetting(List<XVmessClient> xVmessClientList) {
        List<String> clientStrList = new ArrayList<>();
        for (XVmessClient xVmessClient : xVmessClientList) {
            clientStrList.add(xVmessClient.getJson());
        }
        this.settings = "{\n" +
                "  \"clients\": [\n" +
                String.join(",", clientStrList) +
                "  ]\n" +
                "}";
    }
}
