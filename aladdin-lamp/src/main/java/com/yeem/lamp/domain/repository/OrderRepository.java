package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.infrastructure.persistence.entity.OrderDo;

import java.util.List;

public interface OrderRepository {
    Order getById(Long id);
    Order getByOrderNo(String orderNo);
    List<Order> list(Order order);
    IPage<Order> page(int current, int size);
    void insert(Order order);
    void updateById(Order order);
}
