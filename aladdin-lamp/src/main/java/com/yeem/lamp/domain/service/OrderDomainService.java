package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    public void generateOrder(Order order) {
        orderRepository.insert(order);
    }

    public List<Order> list(Order order) {
        return orderRepository.list(order);
    }

    public IPage<Order> pages(int current, int size) {
        return orderRepository.page(current, size);
    }

    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    public void updateById(Order order) {
        orderRepository.updateById(order);
    }

    public void insert(Order packages) {
        orderRepository.insert(packages);
    }

    public Order getByOrderNo(String orderNo) {
        return orderRepository.getByOrderNo(orderNo);
    }
}
