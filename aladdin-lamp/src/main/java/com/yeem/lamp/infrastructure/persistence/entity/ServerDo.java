package com.yeem.lamp.infrastructure.persistence.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.lamp.domain.objvalue.Server;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "aladdin_server", autoResultMap = true)
public class ServerDo extends BaseDo {
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

    public static ServerDo init(Server server) {
        ServerDo serverDo = new ServerDo();
        BeanUtil.copyProperties(server, serverDo);
        return serverDo;
    }


    public Server convertServer() {
        Server server = new Server();
        BeanUtil.copyProperties(this, server);
        return server;
    }
}
