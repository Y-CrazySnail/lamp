package com.yeem.zero.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.*;
import com.yeem.zero.mapper.ZeroOrderMapper;
import com.yeem.zero.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ZeroOrderServiceImpl extends ServiceImpl<ZeroOrderMapper, ZeroOrder> implements IZeroOrderService {

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Autowired
    private IZeroCartService zeroCartService;

    @Autowired
    private IZeroOrderItemService zeroOrderItemService;

    @Autowired
    private IZeroProductService zeroProductService;

    @Autowired
    private IZeroPaymentService zeroPaymentService;

    @Override
    public ZeroOrder order(ZeroOrder zeroOrder) {
        // 获取用户信息
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        zeroOrder.setUserId(zeroUserExtra.getUserId());
        // 订单号
        String orderNo = UUID.fastUUID().toString().replace("-", "");
        zeroOrder.setOrderNo(orderNo);
        // 运费
        zeroOrder.setDeliveryCharge(BigDecimal.valueOf(0));
        if (Constant.ORDER_TYPE_DIRECT.equals(zeroOrder.getType())) {
            orderWithDirect(zeroOrder);
        }
        if (Constant.ORDER_TYPE_INDIRECT.equals(zeroOrder.getType())) {
            orderWithIndirect(zeroOrder);
        }
        zeroOrder.setStatus(Constant.ORDER_STATUS_ORDER);
        zeroOrder.setOrderTime(new Date());
        super.save(zeroOrder);

        zeroOrder.getOrderItemList().forEach(zeroOrderItem -> zeroOrderItem.setOrderId(zeroOrder.getId()));
        zeroOrderItemService.saveBatch(zeroOrder.getOrderItemList());

        PrepayWithRequestPaymentResponse response = zeroPaymentService.wechatPrepay(zeroUserExtra.getWechatOpenId(), zeroOrder);
        zeroOrder.setPrepayWithRequestPaymentResponse(response);

        return zeroOrder;
    }

    /**
     * 直接下单
     *
     * @param zeroOrder 订单信息
     */
    private void orderWithDirect(ZeroOrder zeroOrder) {
        ZeroCart zeroCart = zeroOrder.getCartList().get(0);
        ZeroProduct zeroProduct = zeroProductService.getById(zeroCart.getProductId());
        BigDecimal amount = zeroProduct.getPrice().multiply(BigDecimal.valueOf(zeroCart.getQuantity()));
        List<ZeroOrderItem> zeroOrderItemList = new ArrayList<>();
        ZeroOrderItem zeroOrderItem = new ZeroOrderItem();
        zeroOrderItem.setProductId(zeroCart.getProductId());
        zeroOrderItem.setQuantity(zeroCart.getQuantity());
        zeroOrderItem.setPrice(zeroProduct.getPrice());
        zeroOrderItem.setAmount(amount);
        zeroOrderItem.setZeroProduct(zeroProduct);
        zeroOrderItemList.add(zeroOrderItem);
        zeroOrder.setAmount(amount);
        zeroOrder.setOrderItemList(zeroOrderItemList);
    }

    /**
     * 间接下单（购物车下单）
     *
     * @param zeroOrder 订单信息
     */
    private void orderWithIndirect(ZeroOrder zeroOrder) {
        List<Long> cartIdList = zeroOrder.getCartList().stream().map(ZeroCart::getId).collect(Collectors.toList());
        List<ZeroCart> zeroCartList = zeroCartService.listByIdList(cartIdList);
        BigDecimal amount = new BigDecimal(0);
        List<ZeroOrderItem> zeroOrderItemList = new ArrayList<>();
        for (ZeroCart zeroCart : zeroCartList) {
            amount = amount.add(zeroCart.getZeroProduct().getPrice().multiply(BigDecimal.valueOf(zeroCart.getQuantity())));
            ZeroOrderItem zeroOrderItem = new ZeroOrderItem();
            zeroOrderItem.setProductId(zeroCart.getProductId());
            zeroOrderItem.setQuantity(zeroCart.getQuantity());
            zeroOrderItem.setPrice(zeroCart.getZeroProduct().getPrice());
            zeroOrderItem.setAmount(zeroCart.getZeroProduct().getPrice().multiply(BigDecimal.valueOf(zeroCart.getQuantity())));
            zeroOrderItem.setZeroProduct(zeroCart.getZeroProduct());
            zeroOrderItemList.add(zeroOrderItem);
        }
        zeroOrder.setAmount(amount);
        zeroOrder.setOrderItemList(zeroOrderItemList);
        zeroCartService.removeByIds(cartIdList);
    }
}
