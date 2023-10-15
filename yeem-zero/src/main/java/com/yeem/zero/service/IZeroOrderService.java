package com.yeem.zero.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
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

    ZeroOrder get(Long id);

    /**
     * 根据ID查询
     * @param id ID
     * @return 订单信息
     */
    ZeroOrder getById(Long id);

    List<ZeroOrder> list(String status, String name);

    List<ZeroOrder> distribution(String nickName);

    void remove(Long id);

    Integer getDirectReferrerOrderCount(String username);

    Integer getIndirectReferrerOrderCount(String username);
}
