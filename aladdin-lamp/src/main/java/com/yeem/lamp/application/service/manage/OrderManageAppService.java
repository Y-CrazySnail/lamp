package com.yeem.lamp.application.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.dto.OrderDTO;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.service.manage.OrderManageDomainService;
import com.yeem.lamp.domain.service.manage.PackageManageDomainService;
import com.yeem.lamp.domain.service.manage.ServiceManageDomainService;
import com.yeem.lamp.infrastructure.payment.EPaymentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderManageAppService {

    @Autowired
    private OrderManageDomainService orderDomainService;
    @Autowired
    private PackageManageDomainService packageDomainService;
    @Autowired
    private EPaymentProcessor ePaymentProcessor;
    @Autowired
    private ServiceManageDomainService serviceDomainService;
    @Autowired
    private ISysTelegramService sysTelegramService;
    @Autowired
    private ServerManageAppService serverAppService;

    public OrderDTO getById(Long id) {
        Order order = orderDomainService.getById(id);
        return new OrderDTO(order);
    }

    public List<OrderDTO> list(OrderDTO orderDTO) {
        Order order = orderDTO.convertOrder();
        List<Order> orderList = orderDomainService.list(order);
        return orderList.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    public IPage<OrderDTO> page(int current, int size) {
        IPage<Order> page = orderDomainService.page(current, size);
        IPage<OrderDTO> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(OrderDTO::new).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    public void updateById(OrderDTO orderDTO) {
        Order order = orderDTO.convertOrder();
        orderDomainService.updateById(order);
    }

    public void removeById(Long id) {
        orderDomainService.removeById(id);
    }
}
