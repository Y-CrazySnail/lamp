package com.yeem.lamp.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
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
    private String period;
    private BigDecimal price;
    private String uuid;
    /**
     * 0未生效 1已生效 9已过期
     */
    private String status;
    private String wechat;
    private String email;
    private BigDecimal serviceUp;
    private BigDecimal serviceDown;
    private String surplus;

    public enum TYPE {
        SERVICE("0"),
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
        INVALID("0"),
        VALID("1"),
        LACK("8"),
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
        if (this.serviceUp.add(this.serviceDown).compareTo(BigDecimal.valueOf(this.dataTraffic)) > 0) {
            this.setStatus(STATUS.LACK.value);
        }
        if (this.endDate.before(new Date())) {
            this.setStatus(STATUS.EXPIRED.value);
        }
    }
}
