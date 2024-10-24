package com.yeem.lamp.application.service.manage;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.dto.OrderDTO;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.manage.OrderManageDomainService;
import com.yeem.lamp.domain.service.manage.PackageManageDomainService;
import com.yeem.lamp.domain.service.manage.ServiceManageDomainService;
import com.yeem.lamp.infrastructure.payment.EPaymentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public OrderDTO getOrderById(Long id) {
        Order order = orderDomainService.getById(id);
        return new OrderDTO(order);
    }

    public List<OrderDTO> listOrder(OrderDTO orderDTO) {
        Order order = orderDTO.convertOrder();
        List<Order> orderList = orderDomainService.list(order);
        return orderList.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    public IPage<OrderDTO> pageOrder(int current, int size) {
        IPage<Order> page = orderDomainService.pages(current, size);
        IPage<OrderDTO> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(OrderDTO::new).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    public void updateOrderById(OrderDTO orderDTO) {
        Order order = orderDTO.convertOrder();
        orderDomainService.updateById(order);
    }

    public void place(OrderDTO orderDTO) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        Product product = packageDomainService.getById(orderDTO.getPackageId());
        List<Services> servicesList = serviceDomainService.listByMemberId(orderDTO.getMemberId());
        if (servicesList.isEmpty()) {
            Services services = Services.init(orderDTO.getMemberId(), product.getPlan());
            serviceDomainService.save(services);
            servicesList = serviceDomainService.listByMemberId(orderDTO.getMemberId());
        }
        Services services = servicesList.get(0);
        serviceDomainService.setServiceMonth(services, current);
        if (product.getProductType().isData() && null == services.getCurrentServiceMonth()) {
            throw new RuntimeException("can not place data type product");
        }
        Order order = orderDTO.convertOrder();
        order.createOrder(product.getPlan(), product.getProductType(), services.getId());
        orderDomainService.generateOrder(order);
    }

    /**
     * 调起支付
     *
     * @param orderDTO 订单信息
     * @return 支付信息
     */
    public JsonNode pay(OrderDTO orderDTO) {
        Order order = orderDomainService.getById(orderDTO.getId());
        JsonNode payRes = ePaymentProcessor.prepay(order.getPlan().getPrice(), order.getOrderNo());
        if ("1".equals(payRes.get("code").toString())) {
            order.setTradeNo(payRes.get("trade_no").toString().replace("\"", ""));
            orderDomainService.updateById(order);
        }
        return payRes;
    }
}
