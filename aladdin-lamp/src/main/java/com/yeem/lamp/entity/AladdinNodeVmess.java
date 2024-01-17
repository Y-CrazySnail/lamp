package com.yeem.lamp.entity;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import org.springframework.util.StringUtils;

/**
 * vmess节点
 */
@TableName(value = "aladdin_node_vmess", autoResultMap = true)
public class AladdinNodeVmess extends BaseEntity {
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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Integer getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(Integer serviceYear) {
        this.serviceYear = serviceYear;
    }

    public Integer getServiceMonth() {
        return serviceMonth;
    }

    public void setServiceMonth(Integer serviceMonth) {
        this.serviceMonth = serviceMonth;
    }

    public Long getServiceDown() {
        return serviceDown;
    }

    public void setServiceDown(Long serviceDown) {
        this.serviceDown = serviceDown;
    }

    public Long getServiceUp() {
        return serviceUp;
    }

    public void setServiceUp(Long serviceUp) {
        this.serviceUp = serviceUp;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodePs() {
        return nodePs;
    }

    public void setNodePs(String nodePs) {
        this.nodePs = nodePs;
    }

    public String getNodeAdd() {
        return nodeAdd;
    }

    public void setNodeAdd(String nodeAdd) {
        this.nodeAdd = nodeAdd;
    }

    public String getNodePort() {
        return nodePort;
    }

    public void setNodePort(String nodePort) {
        this.nodePort = nodePort;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getScy() {
        return scy;
    }

    public void setScy(String scy) {
        this.scy = scy;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTls() {
        return tls;
    }

    public void setTls(String tls) {
        this.tls = tls;
    }

    public String getSni() {
        return sni;
    }

    public void setSni(String sni) {
        this.sni = sni;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String convert() {
        StringBuilder url = new StringBuilder();
        url.append("{");
        if (!StringUtils.isEmpty(this.nodePs)) {
            url.append("\"ps\":\"").append(this.nodePs).append("\",");
        }
        if (!StringUtils.isEmpty(this.nodeAdd)) {
            url.append("\"add\":\"").append(this.nodeAdd).append("\",");
        }
        if (!StringUtils.isEmpty(this.nodePort)) {
            url.append("\"port\":\"").append(this.nodePort).append("\",");
        }
        if (!StringUtils.isEmpty(this.nodeId)) {
            url.append("\"id\":\"").append(this.nodeId).append("\",");
        }
        if (!StringUtils.isEmpty(this.aid)) {
            url.append("\"aid\":\"").append(this.aid).append("\",");
        }
        if (!StringUtils.isEmpty(this.scy)) {
            url.append("\"scy\":\"").append(this.scy).append("\",");
        }
        if (!StringUtils.isEmpty(this.net)) {
            url.append("\"net\":\"").append(this.net).append("\",");
        }
        if (!StringUtils.isEmpty(this.type)) {
            url.append("\"type\":\"").append(this.type).append("\",");
        }
        if (!StringUtils.isEmpty(this.host)) {
            url.append("\"host\":\"").append(this.host).append("\",");
        }
        if (!StringUtils.isEmpty(this.path)) {
            url.append("\"path\":\"").append(this.path).append("\",");
        }
        if (!StringUtils.isEmpty(this.tls)) {
            url.append("\"tls\":\"").append(this.tls).append("\",");
        }
        if (!StringUtils.isEmpty(this.sni)) {
            url.append("\"sni\":\"").append(this.sni).append("\",");
        }
        url.deleteCharAt(url.length() - 1);
        url.append("}");
        String base64Url = Base64.encode(url.toString()).replace("=", "");
        return "vmess://" + base64Url;
    }
}