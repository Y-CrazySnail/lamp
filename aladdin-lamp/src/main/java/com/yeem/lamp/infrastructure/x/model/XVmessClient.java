package com.yeem.lamp.infrastructure.x.model;

import cn.hutool.core.date.DateUtil;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import lombok.Data;

@Data
public class XVmessClient {
    private String id;
    private String email;
    private int limitIp;
    private double totalGB;
    private long expiryTime;
    private boolean enable;
    private String tgId;
    private String subId;
    private int reset;

    public String getJson() {
        return "{\"id\":\"" + this.id + "\"," +
                "\"email\":\"" + this.email + "\"," +
                "\"limitIp\":" + this.limitIp + "," +
                "\"totalGB\":" + this.totalGB + "," +
                "\"expiryTime\":" + this.expiryTime + "," +
                "\"enable\":" + this.enable + "," +
                "\"tgId\":\"" + this.tgId + "\"," +
                "\"subId\":\"" + this.subId + "\"," +
                "\"reset\":" + this.reset + "}";
    }

    public XVmessClient(NodeVmess nodeVmess) {
        this.id = nodeVmess.getNodeId();
        this.email = DateUtil.format(nodeVmess.getServiceDate(), "yyyyMMdd")
                + "_" + nodeVmess.getServerId()
                + "_" + nodeVmess.getServiceId()
                + "_" + nodeVmess.getId();
        this.limitIp = 0;
        this.totalGB = 0;
        this.expiryTime = 0;
        this.enable = true;
        this.tgId = "";
        this.subId = DateUtil.format(nodeVmess.getServiceDate(), "yyyyMMdd")
                + "_" + nodeVmess.getServerId()
                + "_" + nodeVmess.getServiceId()
                + "_" + nodeVmess.getId();
        this.reset = 0;
    }
}
