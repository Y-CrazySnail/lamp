package com.yeem.lamp.domain.objvalue;

import lombok.Data;

import java.util.Date;

/**
 * 服务记录
 */
@Data
public class ServiceRecord {
    private Long id;
    private Long serviceId;
    private Long serviceMonthId;
    private Date serviceDate;
    private Long bandwidthUp;
    private Long bandwidthDown;
    private String region;

    public void addBandwidthUp(Long bandwidthUp) {
        this.bandwidthUp = this.bandwidthUp + bandwidthUp;
    }

    public void addBandwidthDown(Long bandwidthDown) {
        this.bandwidthDown = this.bandwidthDown + bandwidthDown;
    }
}