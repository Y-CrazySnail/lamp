package com.yeem.lamp.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.yeem.lamp.application.dto.OrderDTO;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.service.OrderDomainService;
import com.yeem.lamp.domain.service.PackageDomainService;
import com.yeem.lamp.infrastructure.payment.EPaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAppService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private PackageDomainService packageDomainService;
    @Autowired
    private EPaymentProcessor ePaymentProcessor;

    public void save(OrderDTO memberDTO) {

    }

    public void removeById(Long id) {

    }

    public void finish(OrderDTO orderDTO) {

    }

    public void updateById(OrderDTO orderDTO) {
        Order order = orderDTO.convertOrder();
        orderDomainService.updateById(order);
    }

    public OrderDTO getById(Long id) {
        Order order = orderDomainService.getById(id);
        return new OrderDTO(order);
    }

    public IPage<OrderDTO> pages(int current, int size) {
        IPage<Order> page = orderDomainService.pages(current, size);
        IPage<OrderDTO> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(OrderDTO::new).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    public List<OrderDTO> list() {
        List<Order> orderList = orderDomainService.list();
        return orderList.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    public List<OrderDTO> listByMemberId(Long memberId) {
        List<Order> orderList = orderDomainService.listByMemberId(memberId);
        return orderList.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    public void place(OrderDTO orderDTO) {
        Package packageDo = packageDomainService.getById(orderDTO.getPackageId());
        Order order = orderDTO.convertOrder();
        order.createOrder(packageDo.getDataTraffic(), packageDo.getPeriod(), packageDo.getPrice());
        orderDomainService.generateOrder(order);
    }

    public JsonNode pay(OrderDTO orderDTO) {
        Order order = orderDomainService.getById(orderDTO.getId());
        JsonNode payRes = ePaymentProcessor.prepay(order.getPrice(), order.getOrderNo());
        if ("1".equals(payRes.get("code").toString())) {
            order.setTradeNo(payRes.get("trade_no").toString().replace("\"", ""));
            orderDomainService.updateById(order);
        }
        return payRes;
    }
}
