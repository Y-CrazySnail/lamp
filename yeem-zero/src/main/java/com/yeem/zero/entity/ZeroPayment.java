package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;

@TableName(value = "zero_payment", autoResultMap = true)
public class ZeroPayment extends BaseEntity {

    /**
     * 订单ID
     */
    private Long orderId;
    private String orderNo;
    private String transactionId;
    private String tradeType;
    private String tradeState;
    private String tradeStateDesc;
    private String successTime;
    private String bankType;
    private String currency;
    private BigDecimal total;

    public void assign(Transaction transaction) {
        this.setTransactionId(transaction.getTransactionId());
        this.setTradeType(transaction.getTradeType().name());
        this.setTradeState(transaction.getTradeState().name());
        this.setTradeStateDesc(transaction.getTradeStateDesc());
        this.setSuccessTime(transaction.getSuccessTime());
        this.setBankType(transaction.getBankType());
        this.setCurrency(transaction.getAmount().getCurrency());
        this.setTotal(BigDecimal.valueOf(transaction.getAmount().getTotal()));
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
