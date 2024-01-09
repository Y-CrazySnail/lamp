package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "zero_balance_record", autoResultMap = true)
public class ZeroBalanceRecord extends BaseEntity {
    /**
     * 用户ID-对应user表id
     */
    private Long userId;
    /**
     * 0充值 1提现
     */
    private Integer type;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 交易时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dealTime;
    /**
     * 状态 -1申请中 1已完成
     */
    private String state;
    /**
     * 转账截图URL
     */
    private String transferScreenshot;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTransferScreenshot() {
        return transferScreenshot;
    }

    public void setTransferScreenshot(String transferScreenshot) {
        this.transferScreenshot = transferScreenshot;
    }

    public enum Type {
        WITHDRAW("充值", 0),
        TOP_UP("提现", 1);
        private final String name;
        private final Integer value;

        Type(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }
    }
}
