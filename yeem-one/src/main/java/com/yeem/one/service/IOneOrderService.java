package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.one.entity.OneOrder;

public interface IOneOrderService extends IService<OneOrder> {
    OneOrder getByIdWithOther(Long id);

    /**
     * 预下单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder preOrder(OneOrder order);

    /**
     * 下单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder placeOrder(OneOrder order);

    /**
     * 预支付
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder prepayOrder(OneOrder order);

    /**
     * 申请退款
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder refundApply(OneOrder order);

    /**
     * 退款
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder refundOrder(OneOrder order);

    /**
     * 收货
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder deliveryOrder(OneOrder order);

    /**
     * 评价
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder reviewOrder(OneOrder order);

    /**
     * 关闭订单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder closeOrder(OneOrder order);

    void paymentCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode, String orderNo);

    void refundCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode, String orderNo);
}
