package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "proxy_server", autoResultMap = true)
public class Server extends BaseEntity {

    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String xrayDomain;
    private String xrayAccessLog;
    private String xrayErrorLog;
    private String xrayWsPath;
    private String xrayCertificateFile;
    private String xrayKeyFile;
}
