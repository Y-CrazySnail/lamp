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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date orderTime;
    /**
     * 付款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date paymentTime;
    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date shipmentTime;
    /**
     * 预计发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date estimatedShipmentTime;
    /**
     * 成交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date completeTime;
    /**
     * 0未退款 1已退款
     */
    private Integer refundFlag;
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
    private String directReferrerUsername;
    /**
     * 间接推荐人-用户名
     */
    private String indirectReferrerUsername;
    /**
     * 直接推荐人-奖金
     */
    private BigDecimal directBonus;
    /**
     * 间接推荐人-奖金
     */
    private BigDecimal indirectBonus;
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

    public Integer getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(Integer refundFlag) {
        this.refundFlag = refundFlag;
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

    public String getDirectReferrerUsername() {
        return directReferrerUsername;
    }

    public void setDirectReferrerUsername(String directReferrerUsername) {
        this.directReferrerUsername = directReferrerUsername;
    }

    public String getIndirectReferrerUsername() {
        return indirectReferrerUsername;
    }

    public void setIndirectReferrerUsername(String indirectReferrerUsername) {
        this.indirectReferrerUsername = indirectReferrerUsername;
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
