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

    void confirm(ZeroOrder zeroOrder);

    ZeroOrder get(Long id);

    List<ZeroOrder> list(String status);

    void remove(Long id);
}
