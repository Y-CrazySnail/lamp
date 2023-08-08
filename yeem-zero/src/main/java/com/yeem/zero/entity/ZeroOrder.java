package com.yeem.zero.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Date orderTime;
    /**
     * 付款时间
     */
    private Date paymentTime;
    /**
     * 发货时间
     */
    private Date shipmentTime;
    /**
     * 成交时间
     */
    private Date completeTime;
    /**
     * 0未退款 1已退款
     */
    private Date refundFlag;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 直接下单direct 购物车下单indirect
     */
    @TableField(exist = false)
    private String type;
    @TableField(exist = false)
    private List<ZeroOrderItem> orderItemList;
    @TableField(exist = false)
    private ZeroAddress address;
    @TableField(exist = false)
    private List<ZeroCart> cartList;
    @TableField(exist = false)
    private PrepayWithRequestPaymentResponse prepayWithRequestPaymentResponse;

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

    public Date getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(Date refundFlag) {
        this.refundFlag = refundFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
