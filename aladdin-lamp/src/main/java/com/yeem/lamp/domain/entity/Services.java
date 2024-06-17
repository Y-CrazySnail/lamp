package com.yeem.lamp.domain.entity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.Plan;
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
    /**
     * 类型 0周期服务 1数据加量包
     */
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private Integer dataTraffic;
    private Integer period;
    private BigDecimal price;
    private String uuid;
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
        if (this.endDate.before(new Date())) {
            return false;
        }
        if (null == this.serviceUp) {
            this.serviceUp = 0L;
        }
        if (null == this.serviceDown) {
            this.serviceDown = 0L;
        }
        return Long.valueOf(this.dataTraffic) * 1024L * 1024L * 1024L - this.serviceUp - this.serviceDown >= 0;
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
}
