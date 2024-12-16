package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_server")
public class LampServer extends BaseEntity {
    private String apiIp; // x-ui域名

    private Integer apiPort; // x-ui端口

    private String apiUsername; // x-ui用户名

    private String apiPassword; // x-ui密码
}