package com.lamp.domain.objvalue;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        this.syncBandwidth();
        return this.bandwidth - this.bandwidthUp - this.bandwidthDown > 0;
    }

    public void addBandwidth(Integer addBandwidthGB) {
        this.bandwidth = this.bandwidth + (1024L * 1024L * 1024L * addBandwidthGB);
    }

    public void initBandwidth(Integer bandwidthGB) {
        this.bandwidth = 1024L * 1024L * 1024L * bandwidthGB;
    }

    public ServiceRecord generateServiceRecord(Date date, String region) {
        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setServiceId(this.serviceId);
        serviceRecord.setServiceMonthId(this.id);
        serviceRecord.setBandwidthUp(0L);
        serviceRecord.setBandwidthDown(0L);
        serviceRecord.setServiceDate(DateUtil.beginOfDay(date).toJdkDate());
        serviceRecord.setRegion(region);
        return serviceRecord;
    }

    public void syncBandwidth() {
        long up = 0L;
        long down = 0L;
        if (Objects.nonNull(this.serviceRecordList) && !this.serviceRecordList.isEmpty()) {
            for (ServiceRecord serviceRecord : this.serviceRecordList) {
                Integer year = DateUtil.year(serviceRecord.getServiceDate());
                Integer month = DateUtil.month(serviceRecord.getServiceDate()) + 1;
                if (year.equals(this.serviceYear) && month.equals(this.serviceMonth)) {
                    up = up + serviceRecord.getBandwidthUp();
                    down = down + serviceRecord.getBandwidthDown();
                }
            }
        }
        this.bandwidthUp = up;
        this.bandwidthDown = down;
    }

    /**
     * 将指定日期服务记录流量进行重置
     *
     * @param date 日期
     */
    public void resetRecordBandwidth(Date date) {
        if (Objects.nonNull(this.serviceRecordList)) {
            this.serviceRecordList.forEach(serviceRecord -> serviceRecord.resetBandwidth(date));
        }
    }
}