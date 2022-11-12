package com.snail.proxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "proxy_server", autoResultMap = true)
public class Server extends BaseEntity {

    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String xrayDomain;
    private String xrayPort;
    private String xrayAccessLog;
    private String xrayErrorLog;
    private String xrayWsPath;
    private String xrayCertificateFile;
    private String xrayKeyFile;
    private String region;
}
