package com.yeem.zero.service.impl;

import cn.hutool.core.lang.UUID;
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
import com.wechat.pay.java.service.refund.model.Account;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.FundsFromItem;
import com.yeem.common.utils.AESUtils;
import com.yeem.common.utils.OauthUtils;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.entity.ZeroPayment;
import com.yeem.zero.entity.ZeroUserExtra;
import com.yeem.zero.mapper.ZeroPaymentMapper;
import com.yeem.zero.service.IZeroPaymentService;
import com.yeem.zero.service.IZeroUserExtraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ZeroPaymentServiceImpl extends ServiceImpl<ZeroPaymentMapper, ZeroPayment> implements IZeroPaymentService {

    @Value("${wechat.zero.app-id}")
    private String appId;
    /**
     * 商户号
     */
    @Value("${wechat.zero.merchant-id}")
    private String merchantId;
    /**
     * 商户API私钥路径
     */
    @Value("${wechat.zero.private-key-path}")
    private String privateKeyPath;
    /**
     * 商户证书序列号
     */
    @Value("${wechat.zero.merchant-serial-number}")
    private String merchantSerialNumber;
    /**
     * 商户APIV3密钥
     */
    @Value("${wechat.zero.api-v3-key}")
    private String apiV3Key;

    @Autowired
    private IZeroUserExtraService zeroUserExtraService;
    @Autowired
    private ZeroPaymentMapper zeroPaymentMapper;

    @Override
    public PrepayWithRequestPaymentResponse wechatPrepay(ZeroOrder zeroOrder) {
        ZeroUserExtra zeroUserExtra = zeroUserExtraService.get(OauthUtils.getUsername());
        return wechatPrepay(zeroUserExtra.getWechatOpenId(), zeroOrder);
    }

    @Override
    public PrepayWithRequestPaymentResponse wechatPrepay(String openId, ZeroOrder zeroOrder) {
        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();
        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(config).build();
        PrepayRequest request = getPrepayRequest(openId, zeroOrder);
        PrepayWithRequestPaymentResponse payment = service.prepayWithRequestPayment(request);
        payment.setSignType("MD5");
        ZeroPayment zeroPayment = new ZeroPayment();
        zeroPayment.setOrderId(zeroOrder.getId());
        zeroPayment.setOrderNo(zeroOrder.getOrderNo());
        this.save(zeroPayment);
        return payment;
    }

    public void wechatRefund() {
        RSAAutoCertificateConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();
        RefundService refundService = new RefundService.Builder().config(config).build();
//        CreateRequest createRequest = getCreateRequest();
    }

    @Override
    public void callback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode) {
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
            ZeroPayment zeroPayment = this.getByOrderNo(transaction.getOutTradeNo());
            zeroPayment.assign(transaction);
            super.updateById(zeroPayment);
        } catch (ValidationException e) {
            log.error("sign verification failed", e);
        }
    }

    private PrepayRequest getPrepayRequest(String openId, ZeroOrder zeroOrder) {
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(zeroOrder.getAmount().intValue() * 100);
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        Payer payer = new Payer();
        payer.setOpenid(openId);
        request.setPayer(payer);
        request.setDescription("支付测试");
        request.setNotifyUrl("https://edreamroom.com/zero-api/wechat-zero-payment/callback");
        request.setOutTradeNo(zeroOrder.getOrderNo());
        return request;
    }

    private CreateRequest getCreateRequest(ZeroOrder zeroOrder) {
        CreateRequest createRequest = new CreateRequest();

        AmountReq amountReq = new AmountReq();
        amountReq.setTotal(zeroOrder.getAmount().multiply(new BigDecimal(10)).longValue());
        amountReq.setCurrency("CNY");
        amountReq.setRefund(10L);
        FundsFromItem fundsFromItem = new FundsFromItem();
        fundsFromItem.setAmount(zeroOrder.getAmount().longValue() * 100);
        fundsFromItem.setAccount(Account.AVAILABLE);
        amountReq.setFrom((Collections.singletonList(fundsFromItem)));
        createRequest.setAmount(amountReq);
        createRequest.setOutTradeNo(zeroOrder.getOrderNo());
        String outRefundNo = UUID.fastUUID().toString().replace("-", "");
        createRequest.setOutRefundNo(outRefundNo);
        return createRequest;
    }

    @Override
    public ZeroPayment getByOrderNo(String orderNo) {
        QueryWrapper<ZeroPayment> zeroPaymentQueryWrapper = new QueryWrapper<>();
        zeroPaymentQueryWrapper.eq("order_no", orderNo);
        return super.getOne(zeroPaymentQueryWrapper);
    }

    @Override
    public boolean save(ZeroPayment entity) {
        ZeroPayment zeroPayment = this.getByOrderNo(entity.getOrderNo());
        if (StringUtils.isEmpty(zeroPayment)) {
            zeroPaymentMapper.insert(entity);
        }
        return true;
    }
}
