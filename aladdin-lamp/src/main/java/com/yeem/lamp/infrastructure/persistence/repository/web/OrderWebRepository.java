package com.yeem.lamp.infrastructure.persistence.repository.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.infrastructure.persistence.entity.*;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OrderWebRepository {

    @Autowired
    private OrderMapper orderMapper;

    public Order getById(Long id) {
        OrderDo orderDo = orderMapper.selectById(id);
        return orderDo.convertOrder();
    }

    public Order getByOrderNo(String orderNo) {
        LambdaQueryWrapper<OrderDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDo::getOrderNo, orderNo);
        OrderDo orderDo = orderMapper.selectOne(queryWrapper);
        return orderDo.convertOrder();
    }

    public List<Order> list(Order order) {
        LambdaQueryWrapper<OrderDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDo::getDeleteFlag, false);
        queryWrapper.orderByDesc(OrderDo::getOrderTime);
        if (null != order.getMemberId()) {
            queryWrapper.eq(OrderDo::getMemberId, order.getMemberId());
        }
        List<OrderDo> orderDoList = orderMapper.selectList(queryWrapper);
        return orderDoList.stream().map(OrderDo::convertOrder).collect(Collectors.toList());
    }

    public void insert(Order order) {
        OrderDo orderDo = OrderDo.init(order);
        orderMapper.insert(orderDo);
    }

    public void updateById(Order order) {
        OrderDo orderDo = OrderDo.init(order);
        orderMapper.updateById(orderDo);
    }
}
