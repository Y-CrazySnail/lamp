package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.common.entity.BaseEntity;

@TableName(value = "zero_payment", autoResultMap = true)
public class ZeroPayment extends BaseEntity {

    /**
     * 订单ID
     */
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
