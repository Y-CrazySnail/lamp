package com.lamp.domain.objvalue;

import cn.hutool.core.date.DateUtil;
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

    public void resetTodayBandwidth() {
        this.bandwidthUp = 0L;
        this.bandwidthDown = 0L;
    }

    public void resetBandwidth(Date date) {
        if (DateUtil.isSameDay(date, serviceDate)) {
            this.bandwidthUp = 0L;
            this.bandwidthDown = 0L;
        }
    }

    public void addBandwidthUp(Long bandwidthUp) {
        this.bandwidthUp = this.bandwidthUp + bandwidthUp;
    }

    public void addBandwidthDown(Long bandwidthDown) {
        this.bandwidthDown = this.bandwidthDown + bandwidthDown;
    }
}