package com.yeem.lamp.entity;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

/**
 * vmess节点
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    private int multiplyingPower;

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