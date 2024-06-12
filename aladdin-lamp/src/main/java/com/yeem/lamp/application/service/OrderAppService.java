package com.yeem.lamp.application.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.dto.OrderDTO;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.service.OrderDomainService;
import com.yeem.lamp.domain.service.PackageDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import com.yeem.lamp.infrastructure.payment.EPaymentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderAppService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private PackageDomainService packageDomainService;
    @Autowired
    private EPaymentProcessor ePaymentProcessor;
    @Autowired
    private ServiceDomainService serviceDomainService;
    @Autowired
    private ISysTelegramService sysTelegramService;

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
        Product product = packageDomainService.getById(orderDTO.getPackageId());
        List<Services> services = serviceDomainService.listByMemberId(orderDTO.getMemberId());
        if (services.stream().anyMatch(s -> Services.TYPE.SERVICE.getValue().equals(s.getType())
                && (s.getStatus().equals(Services.STATUS.VALID.getValue()) || s.getStatus().equals(Services.STATUS.LACK.getValue())))) {
            throw new RuntimeException("已经购买过服务");
        }
        Order order = orderDTO.convertOrder();
        order.createOrder(product.getPlan());
        orderDomainService.generateOrder(order);
    }

    public JsonNode pay(OrderDTO orderDTO) {
        Order order = orderDomainService.getById(orderDTO.getId());
        JsonNode payRes = ePaymentProcessor.prepay(order.getPlan().getPrice(), order.getOrderNo());
        if ("1".equals(payRes.get("code").toString())) {
            order.setTradeNo(payRes.get("trade_no").toString().replace("\"", ""));
            orderDomainService.updateById(order);
        }
        return payRes;
    }

    public void finish(OrderDTO orderDTO) {
        Order order = orderDomainService.getByOrderNo(orderDTO.getOrderNo());
        if (Order.STATUS.ED.getValue().equals(order.getStatus())) {
            // 已支付
            return;
        }
        List<Services> serviceList = serviceDomainService.listByMemberId(order.getMemberId());
        Long serviceId = null;
        for (Services services : serviceList) {
            if (order.getPlan().getDataTraffic().equals(services.getDataTraffic()) || services.getStatus().equals(Services.STATUS.EXPIRED.getValue())) {
                serviceId = services.getId();
                if (services.getEndDate().before(new Date())) {
                    services.setEndDate(DateUtil.offsetMonth(new Date(), order.getPlan().getPeriod()));
                } else {
                    services.setEndDate(DateUtil.offsetMonth(services.getEndDate(), order.getPlan().getPeriod()));
                }
                services.setDataTraffic(order.getPlan().getDataTraffic());
                services.setStatus(Services.STATUS.VALID.getValue());
                services.setPeriod(order.getPlan().getPeriod());
                services.setPrice(order.getPlan().getPrice());
                serviceDomainService.updateById(services);
                break;
            }
        }
        try {
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("purchase");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            // 用户购买了【时长：#{period}】-【流量：#{dataTraffic}】的【#{price}】元套餐，请注意Crisp客服消息！
            replaceMap.put("period", order.getPlan().getPeriod());
            replaceMap.put("dataTraffic", order.getPlan().getDataTraffic());
            replaceMap.put("price", order.getPlan().getPrice());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
        if (null == serviceId) {
            Services service = new Services();
            service.setMemberId(order.getMemberId());
            service.setBeginDate(new Date());
            service.setEndDate(DateUtil.offsetMonth(new Date(), order.getPlan().getPeriod()).toJdkDate());
            service.setDataTraffic(order.getPlan().getDataTraffic());
            service.setPeriod(order.getPlan().getPeriod());
            service.setPrice(order.getPlan().getPrice());
            service.setUuid(UUID.fastUUID().toString());
            service.setStatus("0");
            serviceDomainService.save(service);
            order.setServiceId(serviceId);
        }
        order.finish();
        orderDomainService.updateById(order);
    }
}
