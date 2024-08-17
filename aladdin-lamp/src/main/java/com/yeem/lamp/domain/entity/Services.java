package com.yeem.lamp.domain.entity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.yeem.lamp.domain.objvalue.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Slf4j
public class Services {
    public static final Long GB = 1024L * 1024L * 1024L;

    private Long id;
    private Long memberId;
    /**
     * 类型 0周期服务 1数据加量包
     */
    private String type;
    private Plan plan;
    private String uuid;
    private Date beginDate;
    private Date endDate;
    private ServiceMonth currentServiceMonth;
    private List<ServiceMonth> serviceMonthList;
    private List<ServiceRecord> serviceRecordList;
    private List<Subscription> subscriptionList;
    private List<NodeVmess> nodeVmessList;
    private List<ServiceTransform> serviceTransformList;

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

    public static Services init(Long memberId, Plan plan) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        Services services = new Services();
        services.setMemberId(memberId);
        services.setPlan(plan);
        services.setUuid(UUID.fastUUID().toString());
        services.setBeginDate(current);
        services.setEndDate(current);
        return services;
    }

    public boolean isValid() {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        return this.endDate.after(current);
    }

    public void addMonth(Integer month) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        if (this.isValid()) {
            this.endDate = DateUtil.offsetMonth(this.endDate, month).toJdkDate();
        } else {
            this.endDate = DateUtil.offsetMonth(current, month).toJdkDate();
        }
    }

    public void addTransferDays(Integer days) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        this.endDate = DateUtil.offsetDay(current, days).toJdkDate();
    }

    public void transferPlan(Plan plan) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        if (this.isValid()) {
            log.info("service:{} is valid", this.id);
            // 未过期、服务结余
            int totalDays = this.plan.getPeriod() * 30;
            long validDays = DateUtil.betweenDay(current, this.endDate, true);
            // 计算剩余价值
            BigDecimal balance = this.plan.getPrice().setScale(2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(validDays))
                    .divide(BigDecimal.valueOf(totalDays), RoundingMode.HALF_UP);
            log.info("service id:{}, balance:{} CNY", this.id, balance);
            int transferDays = plan.getPeriod() * 30 * (balance.divide(plan.getPrice(), RoundingMode.HALF_UP)).setScale(0, RoundingMode.FLOOR).intValue();
            log.info("service id:{}, transfer days:{}", this.id, transferDays);
            ServiceTransform serviceTransform = new ServiceTransform();
            serviceTransform.setServiceId(this.id);
            serviceTransform.setOriginPlan(this.plan);
            serviceTransform.setOriginEndDate(this.endDate);
            // 增加转换天数
            this.addTransferDays(transferDays);
            serviceTransform.setTargetPlan(plan);
            serviceTransform.setTargetEndDate(this.endDate);
            serviceTransform.setTransformDate(current);
        }
    }

    public void generateVmessNode() {
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            NodeVmess nodeVmess = NodeVmess.init(this.uuid,
                    subscription.getName(),
                    subscription.getHost(),
                    subscription.getPort()
            );
            nodeVmessList.add(nodeVmess);
        }
        this.nodeVmessList = nodeVmessList;
    }

    public void generateSubscriptionVmessNode() {
        String endDateStr = "到期:" + DateUtil.format(this.endDate, DatePattern.NORM_DATE_PATTERN);
        NodeVmess nodeVmessDoForTime = NodeVmess.initInformation(endDateStr);
        this.nodeVmessList.add(nodeVmessDoForTime);

        BigDecimal surplus = BigDecimal.valueOf(this.currentServiceMonth.getBandwidth() - this.currentServiceMonth.getBandwidthUp() - this.currentServiceMonth.getBandwidthDown())
                .divide(BigDecimal.valueOf(GB), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
        String surplusStr = "本月剩余:" + surplus + "GB";
        NodeVmess nodeVmessDoForSurplus = NodeVmess.initInformation(surplusStr);
        this.nodeVmessList.add(nodeVmessDoForSurplus);

        String websiteStr = "官网:alamp.cc";
        NodeVmess nodeVmessDoForWebsite = NodeVmess.initInformation(websiteStr);
        this.nodeVmessList.add(nodeVmessDoForWebsite);
    }

    public ServiceMonth generateServiceMonth(Integer year, Integer month) {
        Date begin = DateUtil.beginOfDay(DateUtil.beginOfMonth(new Date())).toJdkDate();
        Date end = DateUtil.beginOfDay(DateUtil.endOfMonth(new Date())).toJdkDate();
        int totalDays = DateUtil.lengthOfMonth(month, false);
        ServiceMonth serviceMonth = new ServiceMonth();
        serviceMonth.setServiceId(this.id);
        serviceMonth.setServiceYear(year);
        serviceMonth.setServiceMonth(month);
        if (year == DateUtil.year(endDate) && month == DateUtil.month(endDate) + 1) {
            long validDays = DateUtil.betweenDay(begin, endDate, true);
            BigDecimal trueBandwidth = BigDecimal.valueOf(validDays).setScale(2, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(totalDays), RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(GB))
                    .multiply(BigDecimal.valueOf(this.plan.getBandwidth()));
            serviceMonth.setBandwidth(trueBandwidth.setScale(0, RoundingMode.HALF_UP).longValue());
        } else if (year == DateUtil.year(beginDate) && month == DateUtil.month(beginDate) + 1) {
            long validDays = DateUtil.betweenDay(beginDate, end, true);
            BigDecimal trueBandwidth = BigDecimal.valueOf(validDays).setScale(2, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(totalDays), RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(GB))
                    .multiply(BigDecimal.valueOf(this.plan.getBandwidth()));
            serviceMonth.setBandwidth(trueBandwidth.setScale(0, RoundingMode.HALF_UP).longValue());
        } else {
            serviceMonth.setBandwidth(GB * this.plan.getBandwidth());
        }
        serviceMonth.setBandwidthUp(0L);
        serviceMonth.setBandwidthDown(0L);
        return serviceMonth;
    }
}
