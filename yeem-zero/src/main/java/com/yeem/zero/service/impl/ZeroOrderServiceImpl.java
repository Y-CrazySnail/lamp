package com.yeem.zero.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroCart;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.entity.ZeroOrderItem;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroOrderMapper;
import com.yeem.zero.service.IZeroCartService;
import com.yeem.zero.service.IZeroOrderItemService;
import com.yeem.zero.service.IZeroOrderService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ZeroOrder order(ZeroOrder zeroOrder) {
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        zeroOrder.setUserId(zeroUserExtra.getUserId());
        zeroOrder.setOrderNo(UUID.fastUUID().toString());
        zeroOrder.setDeliveryCharge(BigDecimal.valueOf(0));
        List<Long> cartIdList = zeroOrder.getCartList().stream().map(ZeroCart::getId).collect(Collectors.toList());
        List<ZeroCart> zeroCartList = zeroCartService.listByIdList(cartIdList);
        zeroCartService.removeByIds(zeroCartList);
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
        zeroOrder.setOrderTime(new Date());
        zeroOrder.setStatus("1");
        super.save(zeroOrder);
        zeroOrderItemList.forEach(zeroOrderItem -> zeroOrderItem.setOrderId(zeroOrder.getId()));
        zeroOrderItemService.saveBatch(zeroOrderItemList);
        zeroOrder.setOrderItemList(zeroOrderItemList);
        return zeroOrder;
    }
}
