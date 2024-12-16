package com.lamp.domain.service.web;

import com.lamp.domain.entity.Order;
import com.lamp.infrastructure.persistence.repository.web.OrderWebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderWebDomainService {

    @Autowired
    private OrderWebRepository orderRepository;

    public void generateOrder(Order order) {
        orderRepository.insert(order);
    }

    public List<Order> list(Order order) {
        return orderRepository.list(order);
    }

    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    public void updateById(Order order) {
        orderRepository.updateById(order);
    }

    public Order getByOrderNo(String orderNo) {
        return orderRepository.getByOrderNo(orderNo);
    }
}
