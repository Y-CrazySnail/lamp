package com.yeem.lamp.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.repository.OrderRepository;
import com.yeem.lamp.domain.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> list() {
        return orderRepository.list();
    }

    public IPage<Order> pages(int current, int size) {
        return orderRepository.pages(current, size);
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

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
