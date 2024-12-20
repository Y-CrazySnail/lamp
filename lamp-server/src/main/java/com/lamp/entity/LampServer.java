package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lamp.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_server")
public class LampServer extends BaseEntity {
    private String apiIp; // x-ui域名

    private Integer apiPort; // x-ui端口

    private String apiUsername; // x-ui用户名

    private String apiPassword; // x-ui密码

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expiryDate; //续费时间

    private String region; // 地区

    private String remark; // 备注

    @TableField(exist = false)
    private List<LampInbound> inboundList;
}