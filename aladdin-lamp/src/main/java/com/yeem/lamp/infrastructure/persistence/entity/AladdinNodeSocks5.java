package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;
import cn.hutool.core.util.StrUtil;

/**
 * vmess节点
 */
@TableName(value = "aladdin_node_socks5", autoResultMap = true)
public class AladdinNodeSocks5 extends BaseEntity {
    private String nodeType;
    private String nodeServer;
    private String nodePort;
    private String nodeUser;
    private String nodePass;
    private String nodeRemark;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeServer() {
        return nodeServer;
    }

    public void setNodeServer(String nodeServer) {
        this.nodeServer = nodeServer;
    }

    public String getNodePort() {
        return nodePort;
    }

    public void setNodePort(String nodePort) {
        this.nodePort = nodePort;
    }

    public String getNodeUser() {
        return nodeUser;
    }

    public void setNodeUser(String nodeUser) {
        this.nodeUser = nodeUser;
    }

    public String getNodePass() {
        return nodePass;
    }

    public void setNodePass(String nodePass) {
        this.nodePass = nodePass;
    }

    public String getNodeRemark() {
        return nodeRemark;
    }

    public void setNodeRemark(String nodeRemark) {
        this.nodeRemark = nodeRemark;
    }

    public String convert() {
        StringBuilder url = new StringBuilder();
        // tg://socks5?
        url.append("tg://socks5?");
        if (!StrUtil.isEmpty(this.nodeServer)) {
            url.append("server=").append(this.nodeServer);
        }
        if (!StrUtil.isEmpty(this.nodePort)) {
            url.append("port=").append(this.nodePort);
        }
        if (!StrUtil.isEmpty(this.nodeUser)) {
            url.append("user=").append(this.nodeUser);
        }
        if (!StrUtil.isEmpty(this.nodePass)) {
            url.append("pass=").append(this.nodePass);
        }
        if (!StrUtil.isEmpty(this.nodeRemark)) {
            url.append("remark=").append(this.nodeRemark);
        }
        return url.toString();
    }
}