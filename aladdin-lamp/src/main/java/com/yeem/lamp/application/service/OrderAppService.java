package com.yeem.lamp.application.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeem.lamp.application.dto.MemberDTO;
import com.yeem.lamp.application.dto.OrderDTO;
import com.yeem.lamp.domain.entity.Order;
import com.yeem.lamp.domain.entity.Package;
import com.yeem.lamp.domain.service.OrderDomainService;
import com.yeem.lamp.domain.service.PackageDomainService;
import com.yeem.lamp.infrastructure.payment.EPaymentProcessor;
import com.yeem.lamp.infrastructure.persistence.entity.OrderDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderAppService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private PackageDomainService packageDomainService;
    @Autowired
    private EPaymentProcessor ePaymentProcessor;

    public List<OrderDTO> list() {
        return null;
    }

    public IPage<OrderDTO> pages(int current, int size) {
        return null;
    }

    public OrderDTO getById(Long id) {
        return null;
    }

    public void updateById(OrderDTO orderDTO) {

    }

    public void save(OrderDTO memberDTO) {

    }

    public void removeById(Long id) {

    }

    public OrderDTO place(OrderDTO orderDTO) {
        Package packageDo = packageDomainService.getById(orderDTO.getPackageId());

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
