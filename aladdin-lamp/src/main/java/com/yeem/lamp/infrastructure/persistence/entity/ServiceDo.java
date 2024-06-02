package com.yeem.lamp.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Service;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "aladdin_service", autoResultMap = true)
public class ServiceDo extends BaseEntity {
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
    @TableField(exist = false)
    private String wechat;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private Double serviceUp;
    @TableField(exist = false)
    private Double serviceDown;
    @TableField(exist = false)
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

    public Service convertService() {
        Service service = new Service();
        service.setId(this.getId());
        service.setMemberId(this.memberId);
        service.setType(type);
        service.setBeginDate(this.getBeginDate());
        service.setEndDate(this.getEndDate());
        service.setDataTraffic(dataTraffic);
        service.setPeriod(period);
        service.setPrice(this.price);
        service.setUuid(this.uuid);
        service.setStatus(this.status);
        return service;
    }
}
