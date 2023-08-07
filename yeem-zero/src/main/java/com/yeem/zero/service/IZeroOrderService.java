package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.entity.ZeroOrder;

import java.util.List;

public interface IZeroOrderService extends IService<ZeroOrder> {
    ZeroOrder order(ZeroOrder zeroOrder);
    List<ZeroOrder> list(String status);
}
