package com.yeem.lamp.infrastructure.persistence.repository.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.infrastructure.persistence.entity.OrderDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OrderManageRepository {

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

    public IPage<Order> page(int current, int size) {
        LambdaQueryWrapper<OrderDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDo::getDeleteFlag, false);
        queryWrapper.orderByDesc(OrderDo::getOrderTime);
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

    public void insert(Order order) {
        OrderDo orderDo = OrderDo.init(order);
        orderMapper.insert(orderDo);
    }

    public void updateById(Order order) {
        OrderDo orderDo = OrderDo.init(order);
        orderMapper.updateById(orderDo);
    }

    public void deleteById(Long id) {
        orderMapper.deleteById(id);
    }
}
