package com.yeem.lamp.domain.objvalue;

import lombok.Data;

/**
 * 服务-月
 */
@Data
public class ServiceMonth {
    private Long id;
    private Long serviceId;
    private Integer serviceYear;
    private Integer serviceMonth;
    private Long bandwidth;
    private Long bandwidthUp;
    private Long bandwidthDown;
}