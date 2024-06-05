package com.yeem.lamp.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Services {
    private Long id;
    private Long memberId;
    /**
     * 类型 0周期服务 1数据加量包
     */
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;
    private String uuid;
    /**
     * 0未生效 1已生效 9已过期
     */
    private String status;
    private String wechat;
    private String email;
    private Double serviceUp;
    private Double serviceDown;
    private String surplus;
}
