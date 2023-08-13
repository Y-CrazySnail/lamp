package com.yeem.zero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
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
        return payment;
    }

    private PrepayRequest getPrepayRequest(String openId, ZeroOrder zeroOrder) {
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(10);
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        Payer payer = new Payer();
        payer.setOpenid(openId);
        request.setPayer(payer);
        request.setDescription("支付测试");
        request.setNotifyUrl("https://edreamroom.com/zero-api/zero-payment/callback");
        request.setOutTradeNo(zeroOrder.getOrderNo());
        return request;
    }
}
