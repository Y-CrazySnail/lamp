package com.yeem.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.one.entity.*;
import com.yeem.one.mapper.OneOrderMapper;
import com.yeem.one.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OneOrderServiceImpl extends ServiceImpl<OneOrderMapper, OneOrder> implements IOneOrderService {

    @Autowired
    private OneOrderMapper mapper;
    @Autowired
    private IOneOrderItemService orderItemService;
    @Autowired
    private IOneCartService cartService;
    @Autowired
    private IOneSpuService spuService;
    @Autowired
    private IOneSkuService skuService;

    @Override
    public OneOrder getByIdWithOther(Long id) {
        OneOrder order = mapper.selectById(id);
        // 查询订单项
        List<OneOrderItem> orderItemList = orderItemService.listByOrderId(id);
        order.setOrderItemList(orderItemList);
        return order;
    }

    /**
     * 预下单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    public OneOrder preOrder(OneOrder order) {
        // -：计算总价
        List<OneCart> cartList = new ArrayList<>();
        List<OneOrderItem> orderItemList = new ArrayList<>();
        // 总价
        int orderAmount = 0;
        for (OneCart cart : order.getCartList()) {
            if (null != cart.getId()) {
                cart = cartService.getByIdWithOther(cart.getId());
                cartList.add(cart);
            } else {
                OneSpu spu = spuService.getById(cart.getSkuId());
                cart.setSpu(spu);
                OneSku sku = skuService.getById(cart.getSkuId());
                cart.setSku(sku);
                cart.setValid(spu.getSpuStatus() && sku.getSkuStatus() && null != sku.getSkuStock() && sku.getSkuStock() >= cart.getQuantity());
                cartList.add(cart);
            }
        }
        for (OneCart cart : cartList) {
            if (cart.getValid()) {
                OneOrderItem orderItem = OneOrderItem.convert(cart);
                orderAmount = orderAmount + cart.getQuantity() * cart.getSku().getSkuPrice();
                orderItemList.add(orderItem);
            }
        }
        order.setOrderAmount(orderAmount);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OneOrder placeOrder(OneOrder order) {
        List<OneCart> cartList = new ArrayList<>();
        List<OneOrderItem> orderItemList = new ArrayList<>();
        // -：计算总价
        int orderAmount = 0;
        for (OneCart cart : order.getCartList()) {
            if (null != cart.getId()) {
                cart = cartService.getByIdWithOther(cart.getId());
                cartList.add(cart);
                // -：清除购物车
                cartService.removeById(cart.getId());
            } else {
                OneSpu spu = spuService.getById(cart.getSkuId());
                cart.setSpu(spu);
                OneSku sku = skuService.getById(cart.getSkuId());
                cart.setSku(sku);
                cart.setValid(spu.getSpuStatus() && sku.getSkuStatus() && null != sku.getSkuStock() && sku.getSkuStock() >= cart.getQuantity());
                cartList.add(cart);
            }
        }
        for (OneCart cart : cartList) {
            if (cart.getValid()) {
                OneOrderItem orderItem = OneOrderItem.convert(cart);
                orderAmount = orderAmount + cart.getQuantity() * cart.getSku().getSkuPrice();
                orderItemList.add(orderItem);
            }
        }
        order.setOrderAmount(orderAmount);
        order.setOrderItemList(orderItemList);
        // todo 设置其他属性
        mapper.insert(order);
        order.getOrderItemList().forEach(orderItem -> orderItem.setOrderId(order.getId()));
        orderItemService.saveBatch(order.getOrderItemList());
        return order;
    }

    @Override
    public OneOrder closeOrder(OneOrder order) {
        return null;
    }
}
