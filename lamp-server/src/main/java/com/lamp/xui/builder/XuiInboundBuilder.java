package com.lamp.xui.builder;

import com.lamp.entity.LampInbound;
import com.lamp.entity.LampServiceMonth;
import com.lamp.xui.entity.XuiInbound;
import com.lamp.xui.entity.XuiVmessSetting;

import java.util.ArrayList;
import java.util.List;

public class XuiInboundBuilder {
    public static XuiInbound convert(LampInbound inbound) {
        XuiInbound xuiInbound = new XuiInbound();
        xuiInbound.setUp(0);
        xuiInbound.setDown(0);
        xuiInbound.setTotal(0);
        xuiInbound.setRemark(inbound.getXuiRemark());
        xuiInbound.setEnable(true);
        xuiInbound.setExpiryTime(0);
        xuiInbound.setPort(inbound.getXuiPort());
        xuiInbound.setProtocol("vmess");
//
//        settings: {
//            "clients":
//        }
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

    public static List<XuiVmessSetting> convert(List<LampServiceMonth> serviceMonthList) {
        List<XuiVmessSetting> xuiVmessSettingList = new ArrayList<>();
        for (LampServiceMonth serviceMonth : serviceMonthList) {
            XuiVmessSetting xuiVmessSetting = new XuiVmessSetting();
            xuiVmessSetting.setId("uuid");
            xuiVmessSetting.setEmail("");
            xuiVmessSetting.setLimitIp(0);
            xuiVmessSetting.setTotalGB(0);
            xuiVmessSetting.setExpiryTime(0);
            xuiVmessSetting.setEnable(true);
            xuiVmessSetting.setSubId("");
            xuiVmessSetting.setReset(0);
            xuiVmessSettingList.add(xuiVmessSetting);
        }
        return xuiVmessSettingList;
//        [
//        {
//            "id": "715af18a-cde6-445a-aef0-499221b2851f",
//                "email": "p6kvfwz8",
//                "limitIp": 0,
//                "totalGB": 0,
//                "expiryTime": 0,
//                "enable": true,
//                "tgId": "",
//                "subId": "h9n9lhrga84612vb",
//                "reset": 0
//        }
//            ]
    }
}
