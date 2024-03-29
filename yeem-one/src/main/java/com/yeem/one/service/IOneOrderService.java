package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 关闭订单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    OneOrder closeOrder(OneOrder order);
}
