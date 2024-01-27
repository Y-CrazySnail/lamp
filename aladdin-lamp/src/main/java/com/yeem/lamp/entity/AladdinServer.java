package com.yeem.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "aladdin_server", autoResultMap = true)
public class AladdinServer extends BaseEntity {
    private String apiIp;
    private int apiPort;
    private String apiUsername;
    private String apiPassword;
    private String nodeRemark;
    private int nodePort;
    private String subscribeIp;
    private int subscribePort;
    private String subscribeNamePrefix;
    private String postscript;
    private int sort;
    private int multiplyingPower;

    public String getApiIp() {
        return apiIp;
    }

    public void setApiIp(String apiIp) {
        this.apiIp = apiIp;
    }

    public int getApiPort() {
        return apiPort;
    }

    public void setApiPort(int apiPort) {
        this.apiPort = apiPort;
    }

    public String getNodeRemark() {
        return nodeRemark;
    }

    public void setNodeRemark(String nodeRemark) {
        this.nodeRemark = nodeRemark;
    }

    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }

    public String getApiUsername() {
        return apiUsername;
    }

    public void setApiUsername(String apiUsername) {
        this.apiUsername = apiUsername;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    public String getSubscribeIp() {
        return subscribeIp;
    }

    public void setSubscribeIp(String subscribeIp) {
        this.subscribeIp = subscribeIp;
    }

    public int getSubscribePort() {
        return subscribePort;
    }

    public void setSubscribePort(int subscribePort) {
        this.subscribePort = subscribePort;
    }

    public String getSubscribeNamePrefix() {
        return subscribeNamePrefix;
    }

    public void setSubscribeNamePrefix(String subscribeNamePrefix) {
        this.subscribeNamePrefix = subscribeNamePrefix;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getMultiplyingPower() {
        return multiplyingPower;
    }

    public void setMultiplyingPower(int multiplyingPower) {
        this.multiplyingPower = multiplyingPower;
    }
}
