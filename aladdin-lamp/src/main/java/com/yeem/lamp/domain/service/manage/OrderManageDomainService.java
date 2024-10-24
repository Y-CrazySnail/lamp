package com.yeem.lamp.domain.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.infrastructure.persistence.repository.manage.OrderManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManageDomainService {

    @Autowired
    private OrderManageRepository orderRepository;

    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    public List<Order> list(Order order) {
        return orderRepository.list(order);
    }

    public IPage<Order> page(int current, int size) {
        return orderRepository.page(current, size);
    }

    public void updateById(Order order) {
        orderRepository.updateById(order);
    }

    public void removeById(Long id) {
        orderRepository.deleteById(id);
    }
}
