package com.snail.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

import java.time.LocalDateTime;

@TableName(value = "demo_bill", autoResultMap = true)
public class Bill extends BaseEntity {

    /**
     * 店铺ID
     */
    private Long barbershopId;
    /**
     * 店铺名称
     */
    private String barbershopName;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员姓名
     */
   private String memberName;
    /**
     * 交易金额
     */
    private String amount;
    /**
     * 交易时间
     */
    private LocalDateTime dealTime;

    public Long getBarbershopId() {
        return barbershopId;
    }

    public void setBarbershopId(Long barbershopId) {
        this.barbershopId = barbershopId;
    }

    public String getBarbershopName() {
        return barbershopName;
    }

    public void setBarbershopName(String barbershopName) {
        this.barbershopName = barbershopName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDateTime getDealTime() {
        return dealTime;
    }

    public void setDealTime(LocalDateTime dealTime) {
        this.dealTime = dealTime;
    }
}
