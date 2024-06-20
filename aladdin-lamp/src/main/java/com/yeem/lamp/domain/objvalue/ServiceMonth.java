package com.yeem.lamp.domain.objvalue;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private List<ServiceRecord> serviceRecordList;

    public boolean isValid() {
        return this.bandwidth - this.bandwidthUp - this.bandwidthDown > 0;
    }

    public ServiceRecord generateServiceRecord(Date current, String region) {
        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setServiceId(this.serviceId);
        serviceRecord.setServiceMonthId(this.id);
        serviceRecord.setBandwidthUp(0L);
        serviceRecord.setBandwidthDown(0L);
        serviceRecord.setServiceDate(current);
        serviceRecord.setRegion(region);
        return serviceRecord;
    }

    public void syncBandwidth() {
        long up = 0L;
        long down = 0L;
        for (ServiceRecord serviceRecord : this.serviceRecordList) {
            Integer year = DateUtil.year(serviceRecord.getServiceDate());
            Integer month = DateUtil.month(serviceRecord.getServiceDate()) + 1;
            if (year.equals(this.serviceYear) && month.equals(this.serviceMonth)) {
                up = up + serviceRecord.getBandwidthUp();
                down = down + serviceRecord.getBandwidthDown();
            }
        }
        this.bandwidthUp = up;
        this.bandwidthDown = down;
    }
}