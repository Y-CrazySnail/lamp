package com.lamp.domain.objvalue;

import lombok.Data;

/**
 * vmess节点
 */
@Data
public class NodeVmess {
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
}