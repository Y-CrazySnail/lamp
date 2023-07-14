package com.snail.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.entity.BaseEntity;

import java.math.BigDecimal;

@TableName(value = "zero_order_after_sale", autoResultMap = true)
public class ZeroOrderAfterSale extends BaseEntity {
    private Long orderId;
    private String type;
    private String status;
    private String reason;
    private BigDecimal amount;
    private BigDecimal explain;
    private String returnWaybillNo;
    private String reshipmentWaybillNo;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getExplain() {
        return explain;
    }

    public void setExplain(BigDecimal explain) {
        this.explain = explain;
    }

    public String getReturnWaybillNo() {
        return returnWaybillNo;
    }

    public void setReturnWaybillNo(String returnWaybillNo) {
        this.returnWaybillNo = returnWaybillNo;
    }

    public String getReshipmentWaybillNo() {
        return reshipmentWaybillNo;
    }

    public void setReshipmentWaybillNo(String reshipmentWaybillNo) {
        this.reshipmentWaybillNo = reshipmentWaybillNo;
    }
}
