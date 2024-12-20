package com.lamp.xui.builder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServiceMonth;
import com.lamp.xui.entity.XuiInbound;
import com.lamp.xui.entity.XuiVmessSetting;
import com.lamp.xui.entity.XuiVmessSettings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class XuiInboundBuilder {
    public static XuiInbound build(LampInbound inbound, List<LampServiceMonth> serviceMonthList) {
        XuiInbound xuiInbound = new XuiInbound();
        xuiInbound.setUp(0);
        xuiInbound.setDown(0);
        xuiInbound.setTotal(0);
        xuiInbound.setRemark(inbound.getXuiRemark());
        xuiInbound.setEnable(true);
        xuiInbound.setExpiryTime(0);
        xuiInbound.setPort(inbound.getXuiPort());
        xuiInbound.setProtocol("vmess");
        XuiInboundBuilder.initSniffing(xuiInbound);
        XuiInboundBuilder.initSteamSettings(xuiInbound);
        List<XuiVmessSetting> xuiVmessSettingList = new ArrayList<>();
        for (LampServiceMonth serviceMonth : serviceMonthList) {
            XuiVmessSetting xuiVmessSetting = new XuiVmessSetting();
            xuiVmessSetting.setId(serviceMonth.getUuid());
            xuiVmessSetting.setEmail("i" + "_" + inbound.getId() + "_s_" + serviceMonth.getId());
            xuiVmessSetting.setLimitIp(0);
            xuiVmessSetting.setTotalGB(0);
            // xuiVmessSetting.setExpiryTime(serviceMonth.getExpiryDate().atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            xuiVmessSetting.setExpiryTime(0);
            xuiVmessSetting.setEnable(true);
            xuiVmessSetting.setSubId("");
            xuiVmessSetting.setReset(0);
            xuiVmessSettingList.add(xuiVmessSetting);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        XuiVmessSettings xuiVmessSettings = new XuiVmessSettings();
        xuiVmessSettings.setClients(xuiVmessSettingList);
        try {
            xuiInbound.setSettings(objectMapper.writeValueAsString(xuiVmessSettings));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return xuiInbound;
    }

    public static XuiInbound build(LampInbound inbound, LampServiceMonth serviceMonth) {
        XuiInbound xuiInbound = new XuiInbound();
        xuiInbound.setId(inbound.getXuiId());
        List<XuiVmessSetting> xuiVmessSettingList = new ArrayList<>();
        XuiVmessSetting xuiVmessSetting = new XuiVmessSetting();
        xuiVmessSetting.setId(serviceMonth.getUuid());
        xuiVmessSetting.setEmail("i" + "_" + inbound.getId() + "_s_" + serviceMonth.getId());
        xuiVmessSetting.setLimitIp(0);
        xuiVmessSetting.setTotalGB(0);
        // xuiVmessSetting.setExpiryTime(serviceMonth.getExpiryDate().atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        xuiVmessSetting.setExpiryTime(0);
        xuiVmessSetting.setEnable(true);
        xuiVmessSetting.setSubId("");
        xuiVmessSetting.setReset(0);
        xuiVmessSettingList.add(xuiVmessSetting);
        ObjectMapper objectMapper = new ObjectMapper();
        XuiVmessSettings xuiVmessSettings = new XuiVmessSettings();
        xuiVmessSettings.setClients(xuiVmessSettingList);
        try {
            xuiInbound.setSettings(objectMapper.writeValueAsString(xuiVmessSettings));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return xuiInbound;
    }

    public static void initSteamSettings(XuiInbound xuiInbound) {
        xuiInbound.setStreamSettings("{\n" +
                "  \"network\": \"tcp\",\n" +
                "  \"security\": \"none\",\n" +
                "  \"externalProxy\": [],\n" +
                "  \"tcpSettings\": {\n" +
                "    \"acceptProxyProtocol\": false,\n" +
                "    \"header\": {\n" +
                "      \"type\": \"none\"\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    public static void initSniffing(XuiInbound xuiInbound) {
        xuiInbound.setSniffing("{\n" +
                "  \"enabled\": true,\n" +
                "  \"destOverride\": [\n" +
                "    \"http\",\n" +
                "    \"tls\",\n" +
                "    \"quic\",\n" +
                "    \"fakedns\"\n" +
                "  ],\n" +
                "  \"metadataOnly\": false,\n" +
                "  \"routeOnly\": false" +
                "}");
    }
}
