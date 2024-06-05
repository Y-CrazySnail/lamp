package com.yeem.lamp.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.entity.OrderDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;

import java.io.Serializable;
import java.util.List;

public interface OrderRepository {

    IPage<Order> pages(int current, int size);
    List<Order> listByMemberId(Long memberId);
    void finish(Order order);

    void place(OrderDo orderDo);
    String pay(OrderDo orderDo);

    void insert(Order order);
    boolean removeByMemberId(Serializable id);


    void updateById(Order order);
    Order getById(Long id);
    List<Order> list();
}
