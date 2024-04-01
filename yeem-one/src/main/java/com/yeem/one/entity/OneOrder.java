package com.yeem.one.entity;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
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
    /**
     * 订单信息
     */
    private String orderNo;
    private String orderName;
    private Integer orderAmount;
    private String orderStatus;
    private String orderRemark;
    private String orderDeliveryType;
    /**
     * 订单时间信息
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeOrder;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timePayment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeLead;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeShipment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeDelivery;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeClose;
    /**
     * 快递信息
     */
    private String expressTrackingNo;
    private Integer expressCharge;
    /**
     * 地址信息
     */
    @TableField(exist = false)
    private Long addressId;
    private String addressName;
    private String addressPhone;
    private String addressProvince;
    private String addressCity;
    private String addressDistrict;
    private String addressStreet;
    private String addressDetail;
    /**
     * 付款信息
     */
    private String paymentTransactionId;
    private String paymentTradeType;
    private String paymentTradeState;
    private String paymentSuccessTime;
    private String paymentBankType;
    private String paymentCurrency;
    private Long paymentTotal;
    /**
     * 退款信息
     */
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
    @TableField(exist = false)
    private PrepayWithRequestPaymentResponse prepayWithRequestPaymentResponse;

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
     * 退款类型
     */
    @Getter
    public enum RefundTypeEnum {
        REFUND("仅退款", "refund"),
        RETURN_AND_REFUND("退货退款", "return-and-refund");
        private final String label;
        private final String value;

        RefundTypeEnum(String label, String value) {
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
