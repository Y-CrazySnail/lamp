package com.yeem.one.entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "one_order", autoResultMap = true)
@Data
public class OneOrder extends BaseEntity {
    private Long tenantId;
    private Long storeId;
    @TableField(exist = false)
    private String storeName;
    private Long userId;
    private String orderNo;
    private String orderName;
    private Integer orderAmount;
    private String orderStatus;
    private String orderRemark;
    private String deliveryType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimatedShipmentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shipmentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completeTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date closeTime;
    private String waybillNo;
    private Integer deliveryCharge;
    @TableField(exist = false)
    private Long addressId;
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
    @TableField(exist = false)
    private List<OneCart> cartList;

    /**
     * 交付方式
     */
    @Getter
    public enum DeliveryTypeEnum {
        EXPRESS_DELIVERY("快递", "express-delivery"),
        SELF_PICKUP("自提", "self-pickup"),
        MERCHANT_FULFILLED("商家配送", "merchant-fulfilled");

        private final String label;
        private final String value;

        DeliveryTypeEnum(String label, String value) {
            this.label = label;
            this.value = value;
        }
    }

    /**
     * 订单状态
     */
    @Getter
    public enum OrderStatusEnum {
        PENDING_PAYMENT("待支付", "pending-payment"),
        PENDING_SHIPMENT("待发货", "pending-shipment"),
        PENDING_DELIVERY("待收货", "pending-delivery"),
        PENDING_REVIEW("待评价", "pending-review"),
        COMPLETE("已完成", "complete"),
        CANCELED("已关闭", "canceled");
        private final String label;
        private final String value;

        OrderStatusEnum(String label, String value) {
            this.label = label;
            this.value = value;
        }
    }

    /**
     * 生成订单编号
     * 生成规则 租户ID + 时间戳(秒) + 用户ID + 一位随机数
     *
     * @return 订单ID
     */
    public static String generateOrderNo(Long tenantId, Long userId) {
        String second = String.valueOf(DateUtil.currentSeconds());
        String random = String.valueOf((int) (Math.random() * 10));
        return tenantId + second + userId + random;
    }

    public void setAddress(OneAddress address) {
        this.addressName = address.getAddressName();
        this.addressPhone = address.getAddressPhone();
        this.addressProvince = address.getAddressProvince();
        this.addressCity = address.getAddressCity();
        this.addressDistrict = address.getAddressDistrict();
        this.addressStreet = address.getAddressStreet();
        this.addressDetail = address.getAddressDetail();
    }
}
