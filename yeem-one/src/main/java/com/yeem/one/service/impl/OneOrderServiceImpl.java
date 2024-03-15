package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.OneOrder;
import com.yeem.one.entity.OneOrderItem;
import com.yeem.one.mapper.OneOrderMapper;
import com.yeem.one.service.IOneOrderItemService;
import com.yeem.one.service.IOneOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OneOrderServiceImpl extends ServiceImpl<OneOrderMapper, OneOrder> implements IOneOrderService {

    @Autowired
    private OneOrderMapper mapper;
    @Autowired
    private IOneOrderItemService oneOrderItemService;

    @Override
    public OneOrder getByIdWithOther(Long id) {
        OneOrder order = mapper.selectById(id);
        // 查询订单项
        List<OneOrderItem> orderItemList = oneOrderItemService.listByOrderId(id);
        order.setOrderItemList(orderItemList);
        return order;
    }
}
