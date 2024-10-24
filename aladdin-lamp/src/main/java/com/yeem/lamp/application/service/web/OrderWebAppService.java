package com.yeem.lamp.application.service.web;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.yeem.im.dto.SysTelegramSendDTO;
import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.dto.OrderDTO;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.entity.Product;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.service.web.OrderWebDomainService;
import com.yeem.lamp.domain.service.web.PackageWebDomainService;
import com.yeem.lamp.domain.service.web.ServiceWebDomainService;
import com.yeem.lamp.infrastructure.payment.EPaymentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderWebAppService {

    @Autowired
    private OrderWebDomainService orderDomainService;
    @Autowired
    private PackageWebDomainService packageDomainService;
    @Autowired
    private EPaymentProcessor ePaymentProcessor;
    @Autowired
    private ServiceWebDomainService serviceDomainService;
    @Autowired
    private ISysTelegramService sysTelegramService;
    @Autowired
    private ServerWebAppService serverAppService;

    public List<OrderDTO> listOrder(OrderDTO orderDTO) {
        Order order = orderDTO.convertOrder();
        List<Order> orderList = orderDomainService.list(order);
        return orderList.stream().map(OrderDTO::new).collect(Collectors.toList());
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

    @Transactional
    public void finish(OrderDTO orderDTO) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        Order order = orderDomainService.getByOrderNo(orderDTO.getOrderNo());
        if (Order.STATUS.ED.getValue().equals(order.getStatus())) {
            // 已支付
            return;
        }
        Services services = serviceDomainService.getById(order.getServiceId());
        if (order.getProductType().isData()) {
            // 增值服务-数据包
            serviceDomainService.setServiceMonth(services, current);
            if (null == services.getCurrentServiceMonth()) {
                services.generateServiceMonth(DateUtil.year(current), DateUtil.month(current) + 1);
            }
            ServiceMonth serviceMonth = services.getCurrentServiceMonth();
            serviceMonth.addBandwidth(order.getPlan().getBandwidth());
        } else {
            // 基础服务
            if (services.getPlan().getBandwidth().equals(order.getPlan().getBandwidth())) {
                log.info("plan:{} is same, add month", order.getPlan().getBandwidth());
                // 计划相同
                services.addMonth(order.getPlan().getPeriod());
            } else {
                // 计划不同
                log.info("plan is not same, need transfer from:{}GB plan to:{}GB", services.getPlan().getBandwidth(), order.getPlan().getBandwidth());
                services.transferPlan(order.getPlan());
                services.addMonth(order.getPlan().getPeriod());
            }
            services.setPlan(order.getPlan());
            services.setBeginDate(current);
            serviceDomainService.setServiceMonth(services, current);
            ServiceMonth serviceMonth = services.getCurrentServiceMonth();
            serviceMonth.initBandwidth(order.getPlan().getBandwidth());
        }
        serviceDomainService.save(services);
        // TG消息通知
        try {
            SysTelegramSendDTO sysTelegramSendDTO = new SysTelegramSendDTO();
            sysTelegramSendDTO.setTemplateName("purchase");
            sysTelegramSendDTO.setTemplateType("telegram");
            Map<String, Object> replaceMap = new HashMap<>();
            // 用户购买了【时长：#{period}】-【流量：#{dataTraffic}】的【#{price}】元套餐，请注意Crisp客服消息！
            replaceMap.put("period", order.getPlan().getPeriod());
            replaceMap.put("dataTraffic", order.getPlan().getBandwidth());
            replaceMap.put("price", order.getPlan().getPrice());
            sysTelegramSendDTO.setReplaceMap(replaceMap);
            sysTelegramService.send(sysTelegramSendDTO);
        } catch (Exception e) {
            log.error("send telegram message error:", e);
        }
        order.finish();
        orderDomainService.updateById(order);
        serverAppService.syncRemoteService(order.getServiceId());
    }
}
