package com.yeem.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "aladdin_service", autoResultMap = true)
public class AladdinService extends BaseEntity {
    private Long memberId;
    private Date beginDate;
    private Date endDate;
    private Integer dataTraffic;
    private String period;
    private BigDecimal price;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDataTraffic() {
        return dataTraffic;
    }

    public void setDataTraffic(Integer dataTraffic) {
        this.dataTraffic = dataTraffic;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
