package com.yeem.lamp.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.lamp.domain.objvalue.Plan;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private Integer period;
    private BigDecimal price;
    private String uuid;
    /**
     * 0未生效 1已生效 9已过期
     */
    private String status;
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

    public enum STATUS {
        /**
         * 未生效
         */
        INVALID("0"),
        /**
         * 生效
         */
        VALID("1"),
        /**
         * 流量超额
         */
        LACK("8"),
        /**
         * 已过期
         */
        EXPIRED("9");
        private final String value;

        STATUS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void calculateStatus() {
        if (this.endDate.before(new Date())) {
            this.setStatus(STATUS.EXPIRED.value);
        } else {
            this.setStatus(STATUS.VALID.getValue());
//            if (this.serviceUp.add(this.serviceDown).compareTo(this.dataTraffic) > 0) {
//                this.setStatus(STATUS.LACK.value);
//            }
        }
    }

    public void dealSurplus() {
//        if (null == serviceUp) {
//            serviceUp = new BigDecimal(0);
//        }
//        if (null == serviceDown) {
//            serviceDown = new BigDecimal(0);
//        }
//        this.surplus = this.dataTraffic
//                .subtract(serviceUp)
//                .subtract(serviceDown)
//                .setScale(2, RoundingMode.HALF_UP)
//                .toString();
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
        if (this.dataTraffic * 1024 * 1024 * 1024 - this.serviceUp - this.serviceDown < 0) {
            return false;
        }
        return true;
    }
}
