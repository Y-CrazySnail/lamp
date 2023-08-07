package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.zero.entity.ZeroOrderItem;

import java.util.List;

public interface IZeroOrderItemService extends IService<ZeroOrderItem> {
    List<ZeroOrderItem> listById(Long orderId);
}
