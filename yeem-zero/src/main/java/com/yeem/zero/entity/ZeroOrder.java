package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@TableName(value = "zero_order", autoResultMap = true)
public class ZeroOrder extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 地址ID
     */
    private Long addressId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 运费
     */
    private BigDecimal deliveryCharge;
    /**
     * 订单总价
     */
    private BigDecimal amount;
    /**
     * 状态 1已下单|待付款 2已付款|待发货 3已发货|待收货 4已收货|待评价 5已完成 0交易关闭 -1退款中
     */
    private String status;
    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    /**
     * 付款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentTime;
    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shipmentTime;
    /**
     * 预计发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimatedShipmentTime;
    /**
     * 成交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completeTime;
    /**
     * 关闭时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date closeTime;
    /**
     * -1退款中 0未发生退款 1已退款
     */
    private Integer refundFlag;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 分销订单标识 0否 1是
     */
    private Boolean distributionFlag;
    /**
     * 直接推荐人-用户名
     */
    private Long directReferrerUserId;
    /**
     * 间接推荐人-用户名
     */
    private Long indirectReferrerUserId;
    /**
     * 直接推荐人-奖金
     */
    private BigDecimal directBonus;
    /**
     * 间接推荐人-奖金
     */
    private BigDecimal indirectBonus;
    /**
     * 付款-商户交易号
     */
    private String paymentTransactionId;
    /**
     * 付款-交易类型 JSAPI
     */
    private String paymentTradeType;
    /**
     * 付款-交易状态
     */
    private String paymentTradeState;
    /**
     * 付款-交易成功时间
     */
    private String paymentSuccessTime;
    /**
     * 付款-银行类型
     */
    private String paymentBankType;
    /**
     * 付款-货币
     */
    private String paymentCurrency;
    /**
     * 付款-总额（分）
     */
    private Long paymentTotal;
    /**
     * 微信支付退款号
     */
    private String refundId;
    /**
     * 商户订单号，原支付交易对应的商户订单号
     */
    private String refundNo;
    /**
     * 退款类型 1仅退款 2退货退款
     */
    private String refundType;
    /**
     * 退款入账账户，当前退款单的退款入账方，有以下几种情况：
     * 1）退回银行卡：{银行名称}{卡类型}{卡尾号}
     * 2）退回支付用户零钱:支付用户零钱
     * 3）退还商户:商户基本账户商户结算银行账户
     * 4）退回支付用户零钱通:支付用户零钱通
     */
    private String refundReceivedAccount;

    /**
     * 退款成功时间
     */
    private String refundSuccessTime;

    /**
     * 退款渠道，可选取值：
     * ORIGINAL: 原路退款
     * BALANCE: 退回到余额
     * OTHER_BALANCE: 原账户异常退到其他余额账户
     * OTHER_BANKCARD: 原银行卡异常退到其他银行卡
     */
    private String refundChannel;

    /**
     * 退款状态，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，
     * 可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款。
     * 可选取值：
     * SUCCESS: 退款成功
     * CLOSED: 退款关闭
     * PROCESSING: 退款处理中
     * ABNORMAL: 退款异常
     */
    private String refundStatus;

    /**
     * 资金账户，退款所使用资金对应的资金账户类型，可选取值：
     * UNSETTLED: 未结算资金
     * AVAILABLE: 可用余额
     * UNAVAILABLE: 不可用余额
     * OPERATION: 运营户
     * BASIC: 基本账户（含可用余额和不可用余额）
     * ECNY_BASIC: 数字人民币基本账户
     */
    private String refundFundsAccount;

    /**
     * 用户退款金额，退款给用户的金额，单位为分，不包含所有优惠券金额
     */
    private Long refundPayerRefund;

    /**
     * 手续费退款金额，手续费退款金额，单位为分
     */
    private Long refundFee;
    /**
     * 物流信息
     */
    @TableField(exist = false)
    private JsonNode logistics;
    /**
     * 直接下单direct 购物车下单indirect
     */
    @TableField(exist = false)
    private String type;
    /**
     * 订单项列表
     */
    @TableField(exist = false)
    private List<ZeroOrderItem> orderItemList;
    /**
     * 订单地址信息
     */
    @TableField(exist = false)
    private ZeroAddress address;
    /**
     * 购物车列表
     */
    @TableField(exist = false)
    private List<ZeroCart> cartList;
    /**
     * 预支付信息
     */
    @TableField(exist = false)
    private PrepayWithRequestPaymentResponse prepayWithRequestPaymentResponse;
    /**
     * 用户信息
     */
    @TableField(exist = false)
    private ZeroUserExtra userExtra;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public BigDecimal getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(BigDecimal deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(Date shipmentTime) {
        this.shipmentTime = shipmentTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(Integer refundFlag) {
        this.refundFlag = refundFlag;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getDistributionFlag() {
        return distributionFlag;
    }

    public void setDistributionFlag(Boolean distributionFlag) {
        this.distributionFlag = distributionFlag;
    }

    public Long getDirectReferrerUserId() {
        return directReferrerUserId;
    }

    public void setDirectReferrerUserId(Long directReferrerUserId) {
        this.directReferrerUserId = directReferrerUserId;
    }

    public Long getIndirectReferrerUserId() {
        return indirectReferrerUserId;
    }

    public void setIndirectReferrerUserId(Long indirectReferrerUserId) {
        this.indirectReferrerUserId = indirectReferrerUserId;
    }

    public BigDecimal getDirectBonus() {
        return directBonus;
    }

    public void setDirectBonus(BigDecimal directBonus) {
        this.directBonus = directBonus;
    }

    public BigDecimal getIndirectBonus() {
        return indirectBonus;
    }

    public void setIndirectBonus(BigDecimal indirectBonus) {
        this.indirectBonus = indirectBonus;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public String getPaymentTradeType() {
        return paymentTradeType;
    }

    public void setPaymentTradeType(String paymentTradeType) {
        this.paymentTradeType = paymentTradeType;
    }

    public String getPaymentTradeState() {
        return paymentTradeState;
    }

    public void setPaymentTradeState(String paymentTradeState) {
        this.paymentTradeState = paymentTradeState;
    }

    public String getPaymentSuccessTime() {
        return paymentSuccessTime;
    }

    public void setPaymentSuccessTime(String paymentSuccessTime) {
        this.paymentSuccessTime = paymentSuccessTime;
    }

    public String getPaymentBankType() {
        return paymentBankType;
    }

    public void setPaymentBankType(String paymentBankType) {
        this.paymentBankType = paymentBankType;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public Long getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(Long paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public String getRefundReceivedAccount() {
        return refundReceivedAccount;
    }

    public void setRefundReceivedAccount(String refundReceivedAccount) {
        this.refundReceivedAccount = refundReceivedAccount;
    }

    public String getRefundSuccessTime() {
        return refundSuccessTime;
    }

    public void setRefundSuccessTime(String refundSuccessTime) {
        this.refundSuccessTime = refundSuccessTime;
    }

    public String getRefundChannel() {
        return refundChannel;
    }

    public void setRefundChannel(String refundChannel) {
        this.refundChannel = refundChannel;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundFundsAccount() {
        return refundFundsAccount;
    }

    public void setRefundFundsAccount(String refundFundsAccount) {
        this.refundFundsAccount = refundFundsAccount;
    }

    public Long getRefundPayerRefund() {
        return refundPayerRefund;
    }

    public void setRefundPayerRefund(Long refundPayerRefund) {
        this.refundPayerRefund = refundPayerRefund;
    }

    public Long getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Long refundFee) {
        this.refundFee = refundFee;
    }

    public List<ZeroOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<ZeroOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public ZeroAddress getAddress() {
        return address;
    }

    public void setAddress(ZeroAddress address) {
        this.address = address;
    }

    public List<ZeroCart> getCartList() {
        return cartList;
    }

    public void setCartList(List<ZeroCart> cartList) {
        this.cartList = cartList;
    }

    public PrepayWithRequestPaymentResponse getPrepayWithRequestPaymentResponse() {
        return prepayWithRequestPaymentResponse;
    }

    public void setPrepayWithRequestPaymentResponse(PrepayWithRequestPaymentResponse prepayWithRequestPaymentResponse) {
        this.prepayWithRequestPaymentResponse = prepayWithRequestPaymentResponse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonNode getLogistics() {
        return logistics;
    }

    public void setLogistics(JsonNode logistics) {
        this.logistics = logistics;
    }

    public ZeroUserExtra getUserExtra() {
        return userExtra;
    }

    public void setUserExtra(ZeroUserExtra userExtra) {
        this.userExtra = userExtra;
    }

    public Date getEstimatedShipmentTime() {
        return estimatedShipmentTime;
    }

    public void setEstimatedShipmentTime(Date estimatedShipmentTime) {
        this.estimatedShipmentTime = estimatedShipmentTime;
    }
}
