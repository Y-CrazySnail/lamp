package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.entity.ZeroOrder;

public interface IZeroOrderService extends IService<ZeroOrder> {
    ZeroOrder order(ZeroOrder zeroOrder);
}
