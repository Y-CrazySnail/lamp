package com.yeem.lamp.domain.entity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.objvalue.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Services {
    public static final Long GB = 1024L * 1024L * 1024L;

    private Long id;
    private Long memberId;
    private Integer dataTraffic;
    private Integer period;
    private BigDecimal price;
    private String uuid;
    private Date beginDate;
    private Date endDate;
    private List<ServiceMonth> serviceMonthList;
    private List<ServiceRecord> serviceRecordList;
    private List<Subscription> subscriptionList;

    /**
     * 类型 0周期服务 1数据加量包
     */
    private String type;


    private String wechat;
    private String email;
    /**
     * 当月归档流量
     */
    private Long serviceArchiveUp;
    private Long serviceArchiveDown;
    private Long serviceTodayUp;
    private Long serviceTodayDown;
    private Long serviceUp;
    private Long serviceDown;
    private String surplus;
    private Plan plan;
    private List<Server> serverList;
    private List<NodeVmess> nodeVmessList;

    public enum TYPE {
        /**
         * 服务
         */
        SERVICE("0"),
        /**
         * 数据包
         */
        DATA("1");
        private final String value;

        TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void dealSurplus() {
        long surplusByte = this.dataTraffic * GB - this.serviceUp - this.serviceDown;
        this.surplus = BigDecimal.valueOf(surplusByte).divide(BigDecimal.valueOf(GB), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP).toString();
    }

    public boolean isValid() {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        return this.endDate.after(current);
    }

    public boolean isDateValid() {
        return this.endDate.after(new Date());
    }

    public void generateVmessNode() {
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        for (Server server : this.serverList) {
            NodeVmess nodeVmess = NodeVmess.init(this.uuid,
                    server.getSubscribeNamePrefix(),
                    server.getSubscribeIp(),
                    server.getSubscribePort()
            );
            nodeVmessList.add(nodeVmess);
        }
        this.nodeVmessList = nodeVmessList;
    }

    public void generateSubscriptionVmessNode() {
        String endDateStr = "到期:" + DateUtil.format(this.endDate, DatePattern.NORM_DATE_PATTERN);
        NodeVmess nodeVmessDoForTime = NodeVmess.initInformation(endDateStr);
        this.nodeVmessList.add(nodeVmessDoForTime);

        this.dealSurplus();
        String surplusStr = "本月剩余:" + this.surplus + "GB";
        NodeVmess nodeVmessDoForSurplus = NodeVmess.initInformation(surplusStr);
        this.nodeVmessList.add(nodeVmessDoForSurplus);

        String websiteStr = "官网:aladdinslamp.cc";
        NodeVmess nodeVmessDoForWebsite = NodeVmess.initInformation(websiteStr);
        this.nodeVmessList.add(nodeVmessDoForWebsite);
    }

    public ServiceMonth generateServiceMonth(Integer year, Integer month) {
        Date begin = DateUtil.beginOfDay(DateUtil.beginOfMonth(new Date())).toJdkDate();
        Date end = DateUtil.beginOfDay(DateUtil.endOfMonth(new Date())).toJdkDate();
        int totalDays = DateUtil.dayOfMonth(begin);
        ServiceMonth serviceMonth = new ServiceMonth();
        serviceMonth.setServiceId(this.id);
        serviceMonth.setServiceYear(year);
        serviceMonth.setServiceMonth(month);
        if (year == DateUtil.year(endDate) && month == DateUtil.month(endDate) + 1) {
            long validDays = DateUtil.betweenDay(begin, endDate, true);
            BigDecimal trueBandwidth = BigDecimal.valueOf(validDays)
                    .divide(BigDecimal.valueOf(totalDays), RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(1024L * 1024L * 1024L));
            serviceMonth.setBandwidth(trueBandwidth.setScale(0, RoundingMode.HALF_UP).longValue());
        } else if (year == DateUtil.year(beginDate) && month == DateUtil.month(beginDate) + 1) {
            long validDays = DateUtil.betweenDay(beginDate, end, true);
            BigDecimal trueBandwidth = BigDecimal.valueOf(validDays)
                    .divide(BigDecimal.valueOf(totalDays), RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(1024L * 1024L * 1024L));
            serviceMonth.setBandwidth(trueBandwidth.setScale(0, RoundingMode.HALF_UP).longValue());
        } else {
            serviceMonth.setBandwidth(1024L * 1024L * 1024L * dataTraffic);
        }
        serviceMonth.setBandwidthUp(0L);
        serviceMonth.setBandwidthDown(0L);
        return serviceMonth;
    }
}
