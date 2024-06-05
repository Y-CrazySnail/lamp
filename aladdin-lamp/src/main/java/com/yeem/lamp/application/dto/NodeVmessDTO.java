package com.yeem.lamp.application.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.NodeVmess;
import lombok.Data;

/**
 * vmess节点
 */
@Data
public class NodeVmessDTO {
    private Long id;
    private Long serviceId;
    private Long serverId;
    private Integer serviceYear;
    private Integer serviceMonth;
    private Long serviceDown;
    private Long serviceUp;
    private String nodeType;
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
    private int sort;
    private int multiplyingPower;

    public NodeVmessDTO(NodeVmess nodeVmess) {
        BeanUtil.copyProperties(nodeVmess, this);
    }

    public NodeVmess convertNodeVmess() {
        NodeVmess nodeVmess = new NodeVmess();
        BeanUtil.copyProperties(this, nodeVmess);
        return nodeVmess;
    }

    public String convert() {
        StringBuilder url = new StringBuilder();
        url.append("{");
        if (!StrUtil.isEmpty(this.nodePs)) {
            url.append("\"ps\":\"").append(this.nodePs).append("\",");
        }
        if (!StrUtil.isEmpty(this.nodeAdd)) {
            url.append("\"add\":\"").append(this.nodeAdd).append("\",");
        }
        if (!StrUtil.isEmpty(this.nodePort)) {
            url.append("\"port\":\"").append(this.nodePort).append("\",");
        }
        if (!StrUtil.isEmpty(this.nodeId)) {
            url.append("\"id\":\"").append(this.nodeId).append("\",");
        }
        if (!StrUtil.isEmpty(this.aid)) {
            url.append("\"aid\":\"").append(this.aid).append("\",");
        }
        if (!StrUtil.isEmpty(this.scy)) {
            url.append("\"scy\":\"").append(this.scy).append("\",");
        }
        if (!StrUtil.isEmpty(this.net)) {
            url.append("\"net\":\"").append(this.net).append("\",");
        }
        if (!StrUtil.isEmpty(this.type)) {
            url.append("\"type\":\"").append(this.type).append("\",");
        }
        if (!StrUtil.isEmpty(this.host)) {
            url.append("\"host\":\"").append(this.host).append("\",");
        }
        if (!StrUtil.isEmpty(this.path)) {
            url.append("\"path\":\"").append(this.path).append("\",");
        }
        if (!StrUtil.isEmpty(this.tls)) {
            url.append("\"tls\":\"").append(this.tls).append("\",");
        }
        if (!StrUtil.isEmpty(this.sni)) {
            url.append("\"sni\":\"").append(this.sni).append("\",");
        }
        url.deleteCharAt(url.length() - 1);
        url.append("}");
        String base64Url = Base64.encode(url.toString()).replace("=", "");
        return "vmess://" + base64Url;
    }
}