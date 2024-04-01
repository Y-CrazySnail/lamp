package com.yeem.one.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import com.yeem.one.entity.*;
import com.yeem.one.mapper.OneOrderMapper;
import com.yeem.one.security.WechatAuthInterceptor;
import com.yeem.one.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OneOrderServiceImpl extends ServiceImpl<OneOrderMapper, OneOrder> implements IOneOrderService {

    @Autowired
    private OneOrderMapper mapper;
    @Autowired
    private IOneTenantService tenantService;
    @Autowired
    private IOneOrderItemService orderItemService;
    @Autowired
    private IOneCartService cartService;
    @Autowired
    private IOneSpuService spuService;
    @Autowired
    private IOneSkuService skuService;
    @Autowired
    private IOneAddressService addressService;
    @Value("${wechat.notify-url}")
    private String WECHAT_NOTIFY_URL;

    @Override
    public OneOrder getByIdWithOther(Long id) {
        OneOrder order = mapper.selectById(id);
        // 查询订单项
        List<OneOrderItem> orderItemList = orderItemService.listByOrderId(id);
        order.setOrderItemList(orderItemList);
        return order;
    }

    public OneOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<OneOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OneOrder::getOrderNo, orderNo);
        return mapper.selectOne(queryWrapper);
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

    /**
     * 下单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OneOrder placeOrder(OneOrder order) {
        Long tenantId = order.getTenantId();
        Long userId = order.getUserId();
        List<OneCart> cartList = new ArrayList<>();
        List<OneOrderItem> orderItemList = new ArrayList<>();
        // -：计算总价
        int orderAmount = 0;
        StrBuilder orderName = new StrBuilder();
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
                orderName.append(orderItem.getSpuName()).append("_").append(orderItem.getSkuName()).append("_");
                orderItemList.add(orderItem);
            }
        }
        order.setOrderAmount(orderAmount);
        order.setOrderItemList(orderItemList);
        order.setTenantId(tenantId);
        order.setUserId(userId);
        order.setOrderNo(OneOrder.generateOrderNo(tenantId, userId));
        order.setOrderName(orderName.toString());
        order.setOrderStatus(OneOrder.OrderStatusEnum.PENDING_PAYMENT.getValue());
        order.setTimeOrder(new Date());
        OneAddress address = addressService.getById(order.getAddressId());
        order.setAddress(address);
        save(order);
        return order;
    }

    /**
     * 预支付 调起支付
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    public OneOrder prepayOrder(OneOrder order) {
        Long tenantId = WechatAuthInterceptor.getTenantId();
        Long userId = WechatAuthInterceptor.getUserId();
        String openId = WechatAuthInterceptor.getOpenId();
        order = mapper.selectById(order.getId());
        OneTenant tenant = tenantService.getById(tenantId);
        String appId = tenant.getWechatAppId();
        String merchantId = tenant.getWechatMerchantId();
        String privateKeyPath = tenant.getWechatPrivateKeyPath();
        String merchantSerialNumber = tenant.getWechatMerchantSerialNumber();
        String apiV3Key = tenant.getWechatApiV3Key();
        String notifyUrl = WECHAT_NOTIFY_URL + "paymentCallback/" + order.getOrderNo();
        log.info("tenantId:{}, userId:{}, openId:{}, appId:{}, merchantId:{}, privateKeyPath:{}, merchantSerialNumber:{}, apiV3Key:{}",
                tenantId, userId, openId, appId, merchantId, privateKeyPath, merchantSerialNumber, apiV3Key);
        // 组装校验配置信息
        RSAAutoCertificateConfig rsaAutoCertificateConfig = new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();
        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(rsaAutoCertificateConfig).build();
        // 组装付款请求体
        PrepayRequest prepayRequest = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(order.getOrderAmount());
        amount.setCurrency("CNY");
        prepayRequest.setAmount(amount);
        prepayRequest.setAppid(appId);
        prepayRequest.setMchid(merchantId);
        Payer payer = new Payer();
        payer.setOpenid(openId);
        prepayRequest.setPayer(payer);
        prepayRequest.setDescription(order.getOrderName());
        prepayRequest.setNotifyUrl(notifyUrl);
        prepayRequest.setOutTradeNo(order.getOrderNo());
        PrepayWithRequestPaymentResponse payment = service.prepayWithRequestPayment(prepayRequest);
        payment.setSignType("MD5");
        order.setPrepayWithRequestPaymentResponse(payment);
        return order;
    }

    /**
     * 申请退款
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    public OneOrder refundApply(OneOrder order) {
        if (order.getRefundAmount() > order.getOrderAmount()) {
            log.error("refund amount is greater than the payment amount");
            throw new RuntimeException("退款金额异常");
        }
        mapper.updateById(order);
        return order;
    }

    /**
     * 退款
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OneOrder refundOrder(OneOrder order) {
        order = getById(order.getId());
        OneTenant tenant = tenantService.getById(order.getTenantId());
        String merchantId = tenant.getWechatMerchantId();
        String privateKeyPath = tenant.getWechatPrivateKeyPath();
        String merchantSerialNumber = tenant.getWechatMerchantSerialNumber();
        String apiV3Key = tenant.getWechatApiV3Key();
        String notifyUrl = WECHAT_NOTIFY_URL + "refundCallback/" + order.getOrderNo();
        // 组装校验配置信息
        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();
        RefundService refundService = new RefundService.Builder().config(config).build();
        // 组装退款请求体
        CreateRequest createRequest = new CreateRequest();
        AmountReq amountReq = new AmountReq();
        amountReq.setTotal(Long.valueOf(order.getOrderAmount()));
        amountReq.setCurrency("CNY");
        amountReq.setRefund(Long.valueOf(order.getRefundAmount()));
        createRequest.setAmount(amountReq);
        createRequest.setOutTradeNo(order.getOrderNo());
        String outRefundNo = UUID.fastUUID().toString().replace("-", "");
        createRequest.setOutRefundNo(outRefundNo);
        createRequest.setNotifyUrl(notifyUrl);
        Refund refund = refundService.create(createRequest);
        log.info("refund response:{}", refund);
        order.setRefundId(refund.getRefundId());
        order.setRefundNo(refund.getOutRefundNo());
        order.setRefundReceivedAccount(refund.getUserReceivedAccount());
        order.setRefundSuccessTime(refund.getSuccessTime());
        order.setRefundChannel(refund.getChannel().name());
        order.setRefundStatus(refund.getStatus().name());
        order.setRefundFundsAccount(refund.getFundsAccount().name());
        order.setRefundPayerRefund(refund.getAmount().getPayerRefund());
        order.setRefundFee(refund.getAmount().getRefundFee());
        mapper.updateById(order);
        return order;
    }

    /**
     * 收货
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OneOrder deliveryOrder(OneOrder order) {
        order.setTimeDelivery(new Date());
        order.setOrderStatus(OneOrder.OrderStatusEnum.PENDING_REVIEW.getValue());
        mapper.updateById(order);
        return order;
    }

    /**
     * 评价
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OneOrder reviewOrder(OneOrder order) {
        return order;
    }

    /**
     * 关闭订单
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OneOrder closeOrder(OneOrder order) {
        order.setOrderStatus(OneOrder.OrderStatusEnum.CANCELED.getValue());
        order.setTimeClose(new Date());
        mapper.updateById(order);
        return order;
    }

    @Override
    public boolean save(OneOrder order) {
        mapper.insert(order);
        order.getOrderItemList().forEach(orderItem -> orderItem.setOrderId(order.getId()));
        orderItemService.saveBatch(order.getOrderItemList());
        return true;
    }


    public void paymentCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode, String orderNo) {
        OneOrder order = getByOrderNo(orderNo);
        OneTenant tenant = tenantService.getById(order.getTenantId());
        String merchantId = tenant.getWechatMerchantId();
        String privateKeyPath = tenant.getWechatPrivateKeyPath();
        String merchantSerialNumber = tenant.getWechatMerchantSerialNumber();
        String apiV3Key = tenant.getWechatApiV3Key();
        String body = null;
        try {
            body = new ObjectMapper().writeValueAsString(objectNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            com.wechat.pay.java.core.notification.RequestParam requestParam =
                    new com.wechat.pay.java.core.notification.RequestParam.Builder()
                            .serialNumber(serialNo)
                            .nonce(nonce)
                            .timestamp(timestamp)
                            .signature(signature)
                            .body(body)
                            .build();
            NotificationConfig config = new RSAAutoCertificateConfig.Builder()
                    .merchantId(merchantId)
                    .privateKeyFromPath(privateKeyPath)
                    .merchantSerialNumber(merchantSerialNumber)
                    .apiV3Key(apiV3Key)
                    .build();
            NotificationParser parser = new NotificationParser(config);
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            log.info("wechat transaction info：{}", transaction);
            order.setOrderStatus(OneOrder.OrderStatusEnum.PENDING_SHIPMENT.getValue());
            order.setPaymentTransactionId(transaction.getTransactionId());
            order.setPaymentTradeType(transaction.getTradeType().name());
            order.setPaymentTradeState(transaction.getTradeState().name());
            order.setPaymentSuccessTime(transaction.getSuccessTime());
            order.setPaymentBankType(transaction.getBankType());
            order.setPaymentCurrency(transaction.getAmount().getCurrency());
            order.setPaymentTotal(Long.valueOf(transaction.getAmount().getTotal()));
            mapper.updateById(order);
        } catch (ValidationException e) {
            log.error("sign verification failed", e);
        }
    }

    public void refundCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode, String orderNo) {
        OneOrder order = getByOrderNo(orderNo);
        OneTenant tenant = tenantService.getById(order.getTenantId());
        String merchantId = tenant.getWechatMerchantId();
        String privateKeyPath = tenant.getWechatPrivateKeyPath();
        String merchantSerialNumber = tenant.getWechatMerchantSerialNumber();
        String apiV3Key = tenant.getWechatApiV3Key();
        String body = null;
        try {
            body = new ObjectMapper().writeValueAsString(objectNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            com.wechat.pay.java.core.notification.RequestParam requestParam =
                    new com.wechat.pay.java.core.notification.RequestParam.Builder()
                            .serialNumber(serialNo)
                            .nonce(nonce)
                            .timestamp(timestamp)
                            .signature(signature)
                            .body(body)
                            .build();
            NotificationConfig config = new RSAAutoCertificateConfig.Builder()
                    .merchantId(merchantId)
                    .privateKeyFromPath(privateKeyPath)
                    .merchantSerialNumber(merchantSerialNumber)
                    .apiV3Key(apiV3Key)
                    .build();
            NotificationParser parser = new NotificationParser(config);
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            log.info("wechat transaction info：{}", transaction);
            order.setRefundSuccessTime(transaction.getSuccessTime());
            order.setRefundFlag(true);
            if (OneOrder.RefundTypeEnum.RETURN_AND_REFUND.getValue().equals(order.getRefundType())) {
                order.setOrderStatus(OneOrder.OrderStatusEnum.CANCELED.getValue());
            }
            mapper.updateById(order);
        } catch (ValidationException e) {
            log.error("sign verification failed", e);
        }
    }
}
