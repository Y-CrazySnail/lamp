package com.yeem.lamp.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.objvalue.Plan;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

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
}
