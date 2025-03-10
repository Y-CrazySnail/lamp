package com.lamp.infrastructure.xui.subscription;

import com.lamp.entity.LampMember;
import com.lamp.entity.LampSubscription;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * vmess节点
 */
@Data
public class NodeVmess {

    public static final Long GB = 1024L * 1024L * 1024L;

    private String nodePs;
    private String nodeAdd;
    private String nodePort;
    private String nodeId;
    private String aid;
    private String scy;
    private String net;
    private String type;
    private String host;
    private String path;
    private String tls;
    private String sni;

    public static NodeVmess init(String uuid, String remark, String ip, int port) {
        NodeVmess nodeVmess = new NodeVmess();
        nodeVmess.setNodePs(remark);
        nodeVmess.setNodeAdd(ip);
        nodeVmess.setNodePort(String.valueOf(port));
        nodeVmess.setNodeId(uuid);
        nodeVmess.setAid("0");
        nodeVmess.setNet("tcp");
        nodeVmess.setType("none");
        nodeVmess.setTls("none");
        return nodeVmess;
    }

    public static NodeVmess initInformation(String uuid, String remark, LampSubscription subscription) {
        NodeVmess nodeVmess = new NodeVmess();
        nodeVmess.setNodePs(remark);
        nodeVmess.setNodeAdd(subscription.getHost());
        nodeVmess.setNodePort(String.valueOf(subscription.getPort()));
        nodeVmess.setNodeId(uuid);
        nodeVmess.setAid("0");
        nodeVmess.setNet("tcp");
        nodeVmess.setType("none");
        nodeVmess.setTls("none");
        return nodeVmess;
    }

    public static List<NodeVmess> generateVmessNode(List<LampSubscription> subscriptionList, String uuid) {
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        for (LampSubscription subscription : subscriptionList) {
            NodeVmess nodeVmess = NodeVmess.init(
                    uuid,
                    subscription.getName(),
                    subscription.getHost(),
                    subscription.getPort()
            );
            nodeVmessList.add(nodeVmess);
        }
        return nodeVmessList;
    }

    public static List<NodeVmess> generateSubscriptionVmessNode(LampMember member, LampSubscription subscription) {
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        String endDateStr = "到期:" + member.getExpiryDate().toString();
        NodeVmess nodeVmessDoForTime = NodeVmess.initInformation(member.getUuid(), endDateStr, subscription);
        nodeVmessList.add(nodeVmessDoForTime);
        if (Objects.isNull(member.getMonthBandwidthUp())) {
            member.setMonthBandwidthUp(0L);
        }
        if (Objects.isNull(member.getMonthBandwidthDown())) {
            member.setMonthBandwidthDown(0L);
        }
        BigDecimal surplus = BigDecimal.valueOf(member.getMonthBandwidth() - member.getMonthBandwidthUp() - member.getMonthBandwidthDown());
        surplus = surplus.divide(BigDecimal.valueOf(GB), 2, RoundingMode.HALF_UP);
        String surplusStr = "本月流量剩余:" + surplus + "GB";
        NodeVmess nodeVmessDoForAll = NodeVmess.initInformation(member.getUuid(), surplusStr, subscription);
        nodeVmessList.add(nodeVmessDoForAll);

        String websiteStr = "官网:alamp.cc";
        NodeVmess nodeVmessDoForWebsite = NodeVmess.initInformation(member.getUuid(), websiteStr, subscription);
        nodeVmessList.add(nodeVmessDoForWebsite);
        return nodeVmessList;
    }
}