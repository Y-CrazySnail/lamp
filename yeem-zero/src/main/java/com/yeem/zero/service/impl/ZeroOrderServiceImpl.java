package com.yeem.zero.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import com.yeem.common.entity.BaseEntity;
import com.yeem.common.utils.LogisticsUtils;
import com.yeem.zero.config.Constant;
import com.yeem.zero.entity.*;
import com.yeem.zero.mapper.ZeroOrderMapper;
import com.yeem.zero.security.WechatAuthInterceptor;
import com.yeem.zero.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yeem.zero.config.Constant.*;

@Slf4j
@Service
public class ZeroOrderServiceImpl extends ServiceImpl<ZeroOrderMapper, ZeroOrder> implements IZeroOrderService {

    @Autowired
    private Environment environment;

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;

    @Autowired
    private IZeroCartService zeroCartService;

    @Autowired
    private IZeroOrderItemService zeroOrderItemService;

    @Autowired
    private IZeroProductService zeroProductService;

    @Autowired
    private IZeroAddressService zeroAddressService;

    @Autowired
    private IZeroBalanceRecordService zeroBalanceRecordService;

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
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.getById(zeroOrder.getUserId());
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
        // 设置地址快照
        ZeroAddress zeroAddress = zeroAddressService.getById(zeroOrder.getAddressId());
        zeroOrder.setAddressName(zeroAddress.getName());
        zeroOrder.setAddressPhone(zeroAddress.getPhone());
        zeroOrder.setAddressProvince(zeroAddress.getProvince());
        zeroOrder.setAddressCity(zeroAddress.getCity());
        zeroOrder.setAddressDistrict(zeroAddress.getDistrict());
        zeroOrder.setAddressStreet(zeroAddress.getStreet());
        zeroOrder.setAddressDetail(zeroAddress.getDetail());
        super.save(zeroOrder);
        // 订单项处理
        zeroOrder.getOrderItemList().forEach(zeroOrderItem -> {
            zeroOrderItem.setOrderId(zeroOrder.getId());
            // 设置商品快照
            ZeroProduct zeroProduct = zeroProductService.getById(zeroOrderItem.getProductId());
            zeroOrderItem.setProductName(zeroProduct.getName());
            zeroOrderItem.setPrice(zeroProduct.getPrice());
            zeroOrderItem.setImageShowPath(zeroProduct.getImageShowPath());
        });
        zeroOrderItemService.saveBatch(zeroOrder.getOrderItemList());
        // 订单名称
        StringBuilder orderName = new StringBuilder();
        for (ZeroOrderItem zeroOrderItem : zeroOrder.getOrderItemList()) {
            orderName.append(zeroOrderItem.getZeroProduct().getName());
        }
        zeroOrder.setOrderName(orderName.toString());
        // 直接分销相关
        calculateDirectBonus(zeroOrder, zeroUserExtra);
        // 间接分销相关
        calculateIndirectBonus(zeroOrder, zeroUserExtra);
        super.updateById(zeroOrder);
        // 预支付信息
        PrepayWithRequestPaymentResponse response = wechatPrepay(zeroOrder);
        zeroOrder.setPrepayWithRequestPaymentResponse(response);
        return zeroOrder;
    }

    @Override
    public ZeroOrder prepay(ZeroOrder zeroOrder) {
        // 获取用户信息
        Long id = WechatAuthInterceptor.getUserId();
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.getById(id);
        if (StringUtils.isEmpty(zeroUserExtra)) {
            throw new RuntimeException("user info is null when save cart");
        }
        zeroOrder = getById(zeroOrder.getId());
        PrepayWithRequestPaymentResponse response = wechatPrepay(zeroOrder);
        zeroOrder.setPrepayWithRequestPaymentResponse(response);
        return zeroOrder;
    }

    @Override
    public void paid(ZeroOrder zeroOrder) {
        zeroOrder.setStatus(Constant.ORDER_STATUS_PAY);
        zeroOrder.setPaymentTime(new Date());
        zeroOrder.setEstimatedShipmentTime(DateUtil.offsetDay(zeroOrder.getPaymentTime(), 3));
        super.updateById(zeroOrder);
    }

    @Override
    public void refund(ZeroOrder zeroOrder) {
        zeroOrder.setRefundFlag(REFUND_ING);
        super.updateById(zeroOrder);
    }

    @Override
    public void cancelRefund(ZeroOrder zeroOrder) {
        zeroOrder.setRefundFlag(REFUND_TODO);
        super.updateById(zeroOrder);
    }

    @Override
    public void close(ZeroOrder zeroOrder) {
        zeroOrder.setStatus(Constant.ORDER_STATUS_CLOSE);
        zeroOrder.setCloseTime(new Date());
        super.updateById(zeroOrder);
    }

    @Override
    public void shipment(ZeroOrder zeroOrder) {
        zeroOrder.setStatus(Constant.ORDER_STATUS_DELIVERY);
        zeroOrder.setShipmentTime(new Date());
        baseMapper.updateById(zeroOrder);
    }

    @Override
    public void confirm(ZeroOrder zeroOrder) {
        zeroOrder = getById(zeroOrder.getId());
        zeroOrder.setStatus(Constant.ORDER_STATUS_RECEIVE);
        zeroOrder.setCompleteTime(new Date());
        super.updateById(zeroOrder);
        // 直接分销处理
        if (!StringUtils.isEmpty(zeroOrder.getDistributionFlag())
                && zeroOrder.getDistributionFlag()) {
            if (!StringUtils.isEmpty(zeroOrder.getDirectReferrerUserId())
                    && !StringUtils.isEmpty(zeroOrder.getDirectBonus())) {
                zeroUserExtraService.subtractTodoBalance(zeroOrder.getDirectReferrerUserId(), zeroOrder.getDirectBonus());
                zeroUserExtraService.addBalance(zeroOrder.getDirectReferrerUserId(), zeroOrder.getDirectBonus());
                ZeroBalanceRecord zeroBalanceRecord = new ZeroBalanceRecord();
                zeroBalanceRecord.setType(ZeroBalanceRecord.Type.WITHDRAW.getValue());
                zeroBalanceRecord.setDealTime(new Date());
                ZeroUserExtra zeroUserExtra = zeroUserExtraService.getById(zeroOrder.getDirectReferrerUserId());
                if (!StringUtils.isEmpty(zeroUserExtra)) {
                    zeroBalanceRecord.setAmount(zeroOrder.getDirectBonus());
                    zeroBalanceRecord.setUserId(zeroUserExtra.getId());
                    zeroBalanceRecord.setBalance(zeroUserExtra.getBalance().add(zeroOrder.getDirectBonus()));
                    zeroBalanceRecordService.save(zeroBalanceRecord);
                }
            }
        }
        // 间接分销处理
        if (!StringUtils.isEmpty(zeroOrder.getDistributionFlag())
                && zeroOrder.getDistributionFlag()) {
            if (!StringUtils.isEmpty(zeroOrder.getIndirectReferrerUserId())
                    && !StringUtils.isEmpty(zeroOrder.getIndirectBonus())) {
                zeroUserExtraService.subtractTodoBalance(zeroOrder.getIndirectReferrerUserId(),
                        zeroOrder.getIndirectBonus());
                zeroUserExtraService.addBalance(zeroOrder.getIndirectReferrerUserId(),
                        zeroOrder.getIndirectBonus());
                ZeroBalanceRecord zeroBalanceRecord = new ZeroBalanceRecord();
                zeroBalanceRecord.setType(ZeroBalanceRecord.Type.WITHDRAW.getValue());
                zeroBalanceRecord.setDealTime(new Date());
                ZeroUserExtra zeroUserExtra = zeroUserExtraService.getById(zeroOrder.getIndirectReferrerUserId());
                if (!StringUtils.isEmpty(zeroUserExtra)) {
                    zeroBalanceRecord.setAmount(zeroOrder.getIndirectBonus());
                    zeroBalanceRecord.setUserId(zeroUserExtra.getId());
                    zeroBalanceRecord.setBalance(zeroUserExtra.getBalance().add(zeroOrder.getIndirectBonus()));
                    zeroBalanceRecordService.save(zeroBalanceRecord);
                }
            }
        }
    }

    @Override
    public ZeroOrder getById(Long id) {
        ZeroOrder zeroOrder = super.getById(id);
        List<ZeroOrderItem> zeroOrderItemList = zeroOrderItemService.listById(id);
        zeroOrder.setOrderItemList(zeroOrderItemList);
        // 物流信息查询
        if (Constant.ORDER_STATUS_DELIVERY.equals(zeroOrder.getStatus())) {
            JsonNode logistics = LogisticsUtils.query(logisticsSecretId, logisticsSecretKey, "", "78714106471365");
            zeroOrder.setLogistics(logistics);
        }
        return zeroOrder;
    }

    @Override
    public List<ZeroOrder> list(Long userId, String status, String name) {
        QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(status)) {
            zeroOrderQueryWrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(name)) {
            zeroOrderQueryWrapper.like("order_name", name);
        }
        zeroOrderQueryWrapper.eq("user_id", userId);
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
    public List<ZeroOrder> distribution(String nickName) {
        Long userId = WechatAuthInterceptor.getUserId();
        List<ZeroOrder> zeroOrderList = baseMapper.distribution(userId, nickName);
        zeroOrderList.forEach(zeroOrder -> {
            log.info("distribution order user id:{}", zeroOrder.getUserId());
            ZeroUserExtra zeroUserExtra = zeroUserExtraService.getById(zeroOrder.getUserId());
            zeroOrder.setUserExtra(zeroUserExtra);
        });
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

    @Override
    public Integer getDirectReferrerOrderCount(Long userId) {
        QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
        zeroOrderQueryWrapper.eq("direct_referrer_user_id", userId);
        zeroOrderQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        return baseMapper.selectCount(zeroOrderQueryWrapper);
    }

    @Override
    public Integer getIndirectReferrerOrderCount(Long userId) {
        QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
        zeroOrderQueryWrapper.eq("indirect_referrer_user_id", userId);
        zeroOrderQueryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.BOOLEAN_FALSE);
        return baseMapper.selectCount(zeroOrderQueryWrapper);
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


    private void calculateDirectBonus(ZeroOrder zeroOrder, ZeroUserExtra zeroUserExtra) {
        Long directReferrerUserId = zeroUserExtra.getDirectReferrerUserId();
        if (StringUtils.isEmpty(directReferrerUserId)) {
            return;
        }
        ZeroUserExtra directZeroUserExtra = zeroUserExtraService.getById(directReferrerUserId);
        // 判断直接分销开关
        if (Constant.BOOLEAN_FALSE.equals(distributionDirectSwitch)) {
            return;
        }
        // 无直接推荐人
        if (StringUtils.isEmpty(directZeroUserExtra)) {
            return;
        }
        // 直接推荐人不是分销商
        if (directZeroUserExtra.getDistributionFlag() != 1) {
            return;
        }
        BigDecimal bound = new BigDecimal(0);
        for (ZeroOrderItem zeroOrderItem : zeroOrder.getOrderItemList()) {
            if (!StringUtils.isEmpty(zeroOrderItem.getZeroProduct().getDirectReferrerRate())
                    && zeroOrderItem.getZeroProduct().getDirectReferrerRate() > 0) {
                // 根据商品佣金比例
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(zeroOrderItem.getZeroProduct().getDirectReferrerRate()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            } else if (!StringUtils.isEmpty(directZeroUserExtra.getDirectReferrerRate())
                    && directZeroUserExtra.getDirectReferrerRate() > 0) {
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
        zeroOrder.setDirectReferrerUserId(directReferrerUserId);
        zeroOrder.setDirectBonus(bound);
        zeroOrder.setDistributionFlag(Boolean.TRUE);
        zeroUserExtraService.addTodoBalance(directReferrerUserId, bound);
    }

    private void calculateIndirectBonus(ZeroOrder zeroOrder, ZeroUserExtra zeroUserExtra) {
        Long indirectReferrerUserId = zeroUserExtra.getIndirectReferrerUserId();
        if (StringUtils.isEmpty(indirectReferrerUserId)) {
            return;
        }
        ZeroUserExtra indirectZeroUserExtra = zeroUserExtraService.getById(indirectReferrerUserId);
        // 判断直接分销开关
        if (Constant.BOOLEAN_FALSE.equals(distributionIndirectSwitch)) {
            return;
        }
        // 无间接推荐人
        if (StringUtils.isEmpty(indirectZeroUserExtra)) {
            return;
        }
        // 间接推荐人不是分销商
        if (indirectZeroUserExtra.getDistributionFlag() != 1) {
            return;
        }
        BigDecimal bound = new BigDecimal(0);
        for (ZeroOrderItem zeroOrderItem : zeroOrder.getOrderItemList()) {
            if (!StringUtils.isEmpty(zeroOrderItem.getZeroProduct().getIndirectReferrerRate())
                    && zeroOrderItem.getZeroProduct().getIndirectReferrerRate() > 0) {
                // 根据商品佣金比例
                bound = bound.add(zeroOrderItem.getAmount()
                        .multiply(BigDecimal.valueOf(zeroOrderItem.getZeroProduct().getIndirectReferrerRate()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            } else if (!StringUtils.isEmpty(indirectZeroUserExtra.getIndirectReferrerRate())
                    && indirectZeroUserExtra.getIndirectReferrerRate() > 0) {
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
        zeroOrder.setIndirectReferrerUserId(indirectReferrerUserId);
        zeroOrder.setIndirectBonus(bound);
        zeroOrder.setDistributionFlag(Boolean.TRUE);
        zeroUserExtraService.addTodoBalance(indirectReferrerUserId, bound);
    }

    @Override
    public boolean updateById(ZeroOrder entity) {
        return super.updateById(entity);
    }

    /**
     * 调起支付
     *
     * @param zeroOrder
     * @return
     */
    private PrepayWithRequestPaymentResponse wechatPrepay(ZeroOrder zeroOrder) {
        String openId = WechatAuthInterceptor.getOpenId();
        String active = environment.getProperty("wechat.active");
        String appId = environment.getProperty("wechat." + active + ".app-id");
        String merchantId = environment.getProperty("wechat." + active + ".merchant-id");
        String privateKeyPath = environment.getProperty("wechat." + active + ".private-key-path");
        String merchantSerialNumber = environment.getProperty("wechat." + active + ".merchant-serial-number");
        String apiV3Key = environment.getProperty("wechat." + active + ".api-v3-key");
        String notifyUrl = environment.getProperty("wechat." + active + ".payment-notify-url");
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
        amount.setTotal(zeroOrder.getAmount().multiply(BigDecimal.valueOf(100)).intValue());
        amount.setCurrency("CNY");
        prepayRequest.setAmount(amount);
        prepayRequest.setAppid(appId);
        prepayRequest.setMchid(merchantId);
        Payer payer = new Payer();
        payer.setOpenid(openId);
        prepayRequest.setPayer(payer);
        prepayRequest.setDescription(zeroOrder.getOrderName());
        prepayRequest.setNotifyUrl(notifyUrl);
        prepayRequest.setOutTradeNo(zeroOrder.getOrderNo());
        PrepayWithRequestPaymentResponse payment = service.prepayWithRequestPayment(prepayRequest);
        payment.setSignType("MD5");
        return payment;
    }

    public void paymentCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode) {
        String active = environment.getProperty("wechat.active");
        String merchantId = environment.getProperty("wechat." + active + ".merchant-id");
        String privateKeyPath = environment.getProperty("wechat." + active + ".private-key-path");
        String merchantSerialNumber = environment.getProperty("wechat." + active + ".merchant-serial-number");
        String apiV3Key = environment.getProperty("wechat." + active + ".api-v3-key");
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
            QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
            zeroOrderQueryWrapper.eq("order_no", transaction.getOutTradeNo());
            ZeroOrder zeroOrder = super.getOne(zeroOrderQueryWrapper);
            zeroOrder.setStatus(Constant.ORDER_STATUS_PAY);
            zeroOrder.setPaymentTransactionId(transaction.getTransactionId());
            zeroOrder.setPaymentTradeType(transaction.getTradeType().name());
            zeroOrder.setPaymentTradeState(transaction.getTradeState().name());
            zeroOrder.setPaymentSuccessTime(transaction.getSuccessTime());
            zeroOrder.setPaymentBankType(transaction.getBankType());
            zeroOrder.setPaymentCurrency(transaction.getAmount().getCurrency());
            zeroOrder.setPaymentTotal(Long.valueOf(transaction.getAmount().getTotal()));
            super.updateById(zeroOrder);
        } catch (ValidationException e) {
            log.error("sign verification failed", e);
        }
    }

    public void wechatRefund(ZeroOrder zeroOrder) {
        zeroOrder = getById(zeroOrder.getId());
        String active = environment.getProperty("wechat.active");
        String merchantId = environment.getProperty("wechat." + active + ".merchant-id");
        String privateKeyPath = environment.getProperty("wechat." + active + ".private-key-path");
        String merchantSerialNumber = environment.getProperty("wechat." + active + ".merchant-serial-number");
        String apiV3Key = environment.getProperty("wechat." + active + ".api-v3-key");
        String notifyUrl = environment.getProperty("wechat." + active + ".refund-notify-url");
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
        amountReq.setTotal(zeroOrder.getAmount().multiply(new BigDecimal(100)).longValue());
        amountReq.setCurrency("CNY");
        amountReq.setRefund(zeroOrder.getRefundAmount().multiply(BigDecimal.valueOf(100)).longValue());
        createRequest.setAmount(amountReq);
        createRequest.setOutTradeNo(zeroOrder.getOrderNo());
        String outRefundNo = UUID.fastUUID().toString().replace("-", "");
        createRequest.setOutRefundNo(outRefundNo);
        createRequest.setNotifyUrl(notifyUrl);
        Refund refund = refundService.create(createRequest);
        log.info("refund response:{}", refund);
        zeroOrder.setRefundId(refund.getRefundId());
        zeroOrder.setRefundNo(refund.getOutRefundNo());
        zeroOrder.setRefundReceivedAccount(refund.getUserReceivedAccount());
        zeroOrder.setRefundSuccessTime(refund.getSuccessTime());
        zeroOrder.setRefundChannel(refund.getChannel().name());
        zeroOrder.setRefundStatus(refund.getStatus().name());
        zeroOrder.setRefundFundsAccount(refund.getFundsAccount().name());
        zeroOrder.setRefundPayerRefund(refund.getAmount().getPayerRefund());
        zeroOrder.setRefundFee(refund.getAmount().getRefundFee());
        super.updateById(zeroOrder);
    }

    public void refundCallback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode) {
        String active = environment.getProperty("wechat.active");
        String merchantId = environment.getProperty("wechat." + active + ".merchant-id");
        String privateKeyPath = environment.getProperty("wechat." + active + ".private-key-path");
        String merchantSerialNumber = environment.getProperty("wechat." + active + ".merchant-serial-number");
        String apiV3Key = environment.getProperty("wechat." + active + ".api-v3-key");
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
            QueryWrapper<ZeroOrder> zeroOrderQueryWrapper = new QueryWrapper<>();
            zeroOrderQueryWrapper.eq("order_no", transaction.getOutTradeNo());
            ZeroOrder zeroOrder = super.getOne(zeroOrderQueryWrapper);
            zeroOrder.setRefundSuccessTime(transaction.getSuccessTime());
            zeroOrder.setRefundFlag(REFUND_ED);
            if (ORDER_REFUND_TYPE_REFUND.equals(zeroOrder.getRefundType())) {
                zeroOrder.setStatus(Constant.ORDER_STATUS_CLOSE);
            }
            super.updateById(zeroOrder);
        } catch (ValidationException e) {
            log.error("sign verification failed", e);
        }
    }
}
