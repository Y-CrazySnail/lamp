package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.repository.OrderRepository;
import com.yeem.lamp.infrastructure.persistence.entity.*;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.OrderMapper;
import com.yeem.lamp.security.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Order getById(Long id) {
        OrderDo orderDo = orderMapper.selectById(id);
        return orderDo.convertOrder();
    }

    @Override
    public Order getByOrderNo(String orderNo) {
        LambdaQueryWrapper<OrderDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDo::getOrderNo, orderNo);
        OrderDo orderDo = orderMapper.selectOne(queryWrapper);
        return orderDo.convertOrder();
    }

    @Override
    public List<Order> list(Order order) {
        LambdaQueryWrapper<OrderDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDo::getDeleteFlag, false);
        if (null != order.getMemberId()) {
            queryWrapper.eq(OrderDo::getMemberId, order.getMemberId());
        }
        List<OrderDo> orderDoList = orderMapper.selectList(queryWrapper);
        return orderDoList.stream().map(OrderDo::convertOrder).collect(Collectors.toList());
    }

    @Override
    public IPage<Order> page(int current, int size) {
        QueryWrapper<OrderDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        IPage<OrderDo> page = new Page<>(current, size);
        page = orderMapper.selectPage(page, queryWrapper);
        IPage<Order> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(OrderDo::convertOrder).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    @Override
    public void insert(Order order) {
        OrderDo orderDo = OrderDo.init(order);
        orderMapper.insert(orderDo);
    }

    @Override
    public void updateById(Order order) {
        OrderDo orderDo = OrderDo.init(order);
        orderMapper.updateById(orderDo);
    }
}
