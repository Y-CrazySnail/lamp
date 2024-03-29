package com.yeem.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeem.one.entity.OneOrderItem;

import java.util.List;

public interface IOneOrderItemService extends IService<OneOrderItem> {
    List<OneOrderItem> listByOrderId(Long orderId);
}
