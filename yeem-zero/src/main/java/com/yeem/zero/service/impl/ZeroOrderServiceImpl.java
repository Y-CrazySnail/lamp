package com.yeem.zero.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.LogisticsUtils;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;

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

    @Autowired
    private IZeroAddressService zeroAddressService;

    @Value("${logistics.secret-id}")
    private String logisticsSecretId;

    @Value("${logistics.secret-key}")
    private String logisticsSecretKey;

    @Value("${distribution.direct.switch}")
    private String distributionDirectSwitch;

    @Value("${distribution.indirect.switch}")
    private String distributionIndirectSwitch;

    @Value("${distribution.direct.global-rate}")
    private Integer distributionDirectGlobalRate;

    @Value("${distribution.indirect.global-rate}")
    private Integer distributionIndirectGlobalRate;

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
        // 订单项组装
        if (Constant.ORDER_TYPE_DIRECT.equals(zeroOrder.getType())) {
            orderWithDirect(zeroOrder);
        }
        if (Constant.ORDER_TYPE_INDIRECT.equals(zeroOrder.getType())) {
            orderWithIndirect(zeroOrder);
        }
        zeroOrder.setStatus(Constant.ORDER_STATUS_ORDER);
        zeroOrder.setOrderTime(new Date());
        super.save(zeroOrder);
        // 订单项处理
        zeroOrder.getOrderItemList().forEach(zeroOrderItem -> zeroOrderItem.setOrderId(zeroOrder.getId()));
        zeroOrderItemService.saveBatch(zeroOrder.getOrderItemList());
        // 订单名称
        StringBuilder orderName = new StringBuilder();
        for (ZeroOrderItem zeroOrderItem : zeroOrder.getOrderItemList()) {
            orderName.append(zeroOrderItem.getZeroProduct().getName());
        }
        zeroOrder.setOrderName(orderName.toString());
        // 分销相关
        BigDecimal directBound = calculateDirectBonus(zeroOrder, zeroUserExtra);
        zeroOrder.setDirectReferrerUsername(zeroUserExtra.getDirectReferrerUsername());
        zeroOrder.setDirectBonus(directBound);
        BigDecimal indirectBound = calculateIndirectBonus(zeroOrder, zeroUserExtra);
        zeroOrder.setIndirectReferrerUsername(zeroUserExtra.getIndirectReferrerUsername());
        zeroOrder.setIndirectBonus(indirectBound);
        super.updateById(zeroOrder);
        // 预支付信息
        PrepayWithRequestPaymentResponse response = zeroPaymentService.wechatPrepay(zeroUserExtra.getWechatOpenId(), zeroOrder);
        zeroOrder.setPrepayWithRequestPaymentResponse(response);

        return zeroOrder;
    }

    @Override
    public ZeroOrder prepay(ZeroOrder zeroOrder) {
        // 获取用户信息
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        zeroOrder = get(zeroOrder.getId());
        PrepayWithRequestPaymentResponse response = zeroPaymentService.wechatPrepay(zeroUserExtra.getWechatOpenId(), zeroOrder);
        zeroOrder.setPrepayWithRequestPaymentResponse(response);
        return zeroOrder;
    }

    @Override
    public void paid(ZeroOrder zeroOrder) {
        zeroOrder.setStatus(Constant.ORDER_STATUS_PAY);
        zeroOrder.setPaymentTime(new Date());
        super.updateById(zeroOrder);
    }

    @Override
    public void confirm(ZeroOrder zeroOrder) {
        zeroOrder.setStatus(Constant.ORDER_STATUS_RECEIVE);
        zeroOrder.setCompleteTime(new Date());
        super.updateById(zeroOrder);
    }

    @Override
    public ZeroOrder get(Long id) {
        String username = OauthUtils.getUsername();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(username);
        QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
        zeroOrderQueryWrapper.eq("user_id", zeroUserExtra.getUserId());
        zeroOrderQueryWrapper.eq("id", id);
        ZeroOrder zeroOrder = super.getOne(zeroOrderQueryWrapper);
        List<ZeroOrderItem> zeroOrderItemList = zeroOrderItemService.listById(id);
        zeroOrder.setOrderItemList(zeroOrderItemList);
        ZeroAddress zeroAddress = zeroAddressService.getById(zeroOrder.getAddressId());
        zeroOrder.setAddress(zeroAddress);
        // 物流信息查询
        if (Constant.ORDER_STATUS_DELIVERY.equals(zeroOrder.getStatus())) {
            JsonNode logistics = LogisticsUtils.query(logisticsSecretId, logisticsSecretKey, "", "78714106471365");
            zeroOrder.setLogistics(logistics);
        }
        return zeroOrder;
    }

    @Override
    public List<ZeroOrder> list(String status, String name) {
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(OauthUtils.getUsername());
        QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(status)) {
            zeroOrderQueryWrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(name)) {
            zeroOrderQueryWrapper.like("order_name", name);
        }
        zeroOrderQueryWrapper.eq("user_id", zeroUserExtra.getUserId());
        zeroOrderQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        zeroOrderQueryWrapper.orderByDesc(BaseEntity.BaseField.UPDATE_TIME.getName());
        List<ZeroOrder> zeroOrderList = super.list(zeroOrderQueryWrapper);
        if (!zeroOrderList.isEmpty()) {
            zeroOrderList.forEach(zeroOrder -> {
                List<ZeroOrderItem> zeroOrderItemList = zeroOrderItemService.listById(zeroOrder.getId());
                zeroOrder.setOrderItemList(zeroOrderItemList);
            });
        }
        return zeroOrderList;
    }

    @Override
    public void remove(Long id) {
        UpdateWrapper<ZeroOrder> zeroOrderUpdateWrapper = new UpdateWrapper<>();
        zeroOrderUpdateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_TRUE);
        zeroOrderUpdateWrapper.eq(BaseEntity.BaseField.ID.getName(), id);
        super.remove(zeroOrderUpdateWrapper);
        UpdateWrapper<ZeroOrderItem> zeroOrderItemUpdateWrapper = new UpdateWrapper<>();
        zeroOrderItemUpdateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_TRUE);
        zeroOrderItemUpdateWrapper.eq(BaseEntity.BaseField.ID.getName(), id);
        zeroOrderItemService.remove(zeroOrderItemUpdateWrapper);
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


    private BigDecimal calculateDirectBonus(ZeroOrder zeroOrder, ZeroUserExtra zeroUserExtra) {
        String directReferrerUsername = zeroUserExtra.getDirectReferrerUsername();
        ZeroUserExtra directZeroUserExtra = zeroUserExtraService.get(directReferrerUsername);
        // 判断直接分销开关
        if (Constant.BOOLEAN_FALSE.equals(distributionDirectSwitch)) {
            return null;
        }
        if (!directZeroUserExtra.getDistributionFlag()) {
            return null;
        }
        BigDecimal bound = new BigDecimal(0);
        for (ZeroOrderItem zeroOrderItem : zeroOrder.getOrderItemList()) {
            if (zeroOrderItem.getZeroProduct().getDirectReferrerRate() > 0) {
                // 根据商品佣金比例
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(zeroOrderItem.getZeroProduct().getDirectReferrerRate()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            } else if (directZeroUserExtra.getDirectReferrerRate() > 0) {
                // 根据分销商佣金比例
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(directZeroUserExtra.getDirectReferrerRate()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            } else {
                // 根据全局
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(distributionDirectGlobalRate))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            }
        }
        return bound;
    }

    private BigDecimal calculateIndirectBonus(ZeroOrder zeroOrder, ZeroUserExtra zeroUserExtra) {
        String indirectReferrerUsername = zeroUserExtra.getIndirectReferrerUsername();
        ZeroUserExtra indirectZeroUserExtra = zeroUserExtraService.get(indirectReferrerUsername);
        // 判断直接分销开关
        if (Constant.BOOLEAN_FALSE.equals(distributionIndirectSwitch)) {
            return null;
        }
        if (!indirectZeroUserExtra.getDistributionFlag()) {
            return null;
        }
        BigDecimal bound = new BigDecimal(0);
        for (ZeroOrderItem zeroOrderItem : zeroOrder.getOrderItemList()) {
            if (zeroOrderItem.getZeroProduct().getIndirectReferrerRate() > 0) {
                // 根据商品佣金比例
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(zeroOrderItem.getZeroProduct().getIndirectReferrerRate()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            } else if (indirectZeroUserExtra.getIndirectReferrerRate() > 0) {
                // 根据分销商佣金比例
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(indirectZeroUserExtra.getDirectReferrerRate()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            } else {
                // 根据全局
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(distributionIndirectGlobalRate))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            }
        }
        return bound;
    }
}
