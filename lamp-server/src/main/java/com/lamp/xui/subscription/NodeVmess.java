package com.lamp.xui.subscription;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.lamp.domain.objvalue.Subscription;
import com.lamp.entity.LampServiceMonth;
import com.lamp.entity.LampSubscription;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static NodeVmess initInformation(String remark) {
        NodeVmess nodeVmess = new NodeVmess();
        nodeVmess.setNodePs(remark);
        nodeVmess.setNodeAdd("127.0.0.1");
        nodeVmess.setNodePort(String.valueOf(33333));
        nodeVmess.setNodeId("00000000-0000-0000-0000-000000000000");
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

    public static List<NodeVmess> generateSubscriptionVmessNode(LampServiceMonth serviceMonth) {
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        String endDateStr = "到期:" + serviceMonth.getExpiryDate().toString();
        NodeVmess nodeVmessDoForTime = NodeVmess.initInformation(endDateStr);
        nodeVmessList.add(nodeVmessDoForTime);
        BigDecimal surplus = BigDecimal.valueOf(serviceMonth.getBandwidth() - serviceMonth.getBandwidthUp() - serviceMonth.getBandwidthDown());
        surplus = surplus.divide(BigDecimal.valueOf(GB), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
        String surplusStr = "本月流量剩余:" + surplus + "GB";
        NodeVmess nodeVmessDoForAll = NodeVmess.initInformation(surplusStr);
        nodeVmessList.add(nodeVmessDoForAll);

        String websiteStr = "官网:alamp.cc";
        NodeVmess nodeVmessDoForWebsite = NodeVmess.initInformation(websiteStr);
        nodeVmessList.add(nodeVmessDoForWebsite);
        return nodeVmessList;
    }
}