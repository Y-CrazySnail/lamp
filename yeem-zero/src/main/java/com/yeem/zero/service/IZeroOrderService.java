package com.yeem.zero.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.entity.ZeroOrder;

import java.util.List;

public interface IZeroOrderService extends IService<ZeroOrder> {

    ZeroOrder order(ZeroOrder zeroOrder);

    ZeroOrder prepay(ZeroOrder zeroOrder);

    void paid(ZeroOrder zeroOrder);

    void refund(ZeroOrder zeroOrder);

    void cancelRefund(ZeroOrder zeroOrder);

    void close(ZeroOrder zeroOrder);

    void shipment(ZeroOrder zeroOrder);

    void confirm(ZeroOrder zeroOrder);

    /**
     * 根据ID查询
     * @param id ID
     * @return 订单信息
     */
    ZeroOrder getById(Long id);

    List<ZeroOrder> list(Long userId, String status, String name);

    List<ZeroOrder> distribution(String nickName);

    void remove(Long id);

    Integer getDirectReferrerOrderCount(Long userId);

    Integer getIndirectReferrerOrderCount(Long userId);

    void paymentCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode);

    void wechatRefund(ZeroOrder zeroOrder);

    void refundCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode);
}
