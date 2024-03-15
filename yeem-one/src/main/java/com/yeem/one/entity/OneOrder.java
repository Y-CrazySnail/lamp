package com.yeem.one.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_order", autoResultMap = true)
@Data
public class OneOrder extends BaseEntity {
    private Long tenantId;
    private Long storeId;
    private Long userId;
    private String orderNo;
    private String orderName;
    private Integer orderAmount;
    private String orderStatus;
    private String orderRemark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date paymentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date estimatedShipmentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date shipmentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date completeTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date closeTime;
    private String waybillNo;
    private Integer deliveryCharge;
    private String addressName;
    private String addressPhone;
    private String addressProvince;
    private String addressCity;
    private String addressDistrict;
    private String addressStreet;
    private String addressDetail;
    private String paymentTransactionId;
    private String paymentTradeType;
    private String paymentTradeState;
    private String paymentSuccessTime;
    private String paymentBankType;
    private String paymentCurrency;
    private Long paymentTotal;
    private Boolean refundFlag;
    private String refundType;
    private Integer refundAmount;
    private String refundReason;
    private String refundId;
    private String refundNo;
    private String refundReceivedAccount;
    private String refundSuccessTime;
    private String refundChannel;
    private String refundStatus;
    private String refundFundsAccount;
    private Long refundPayerRefund;
    private Long refundFee;
    @TableField(exist = false)
    private List<OneOrderItem> orderItemList;
}
