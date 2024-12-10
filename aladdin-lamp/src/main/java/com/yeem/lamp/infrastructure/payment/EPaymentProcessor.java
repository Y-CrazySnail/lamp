package com.yeem.lamp.infrastructure.payment;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EPaymentProcessor {

    @Autowired
    private Environment environment;

    public JsonNode prepay(BigDecimal price, String orderNo) {
        String merchantApi = environment.getProperty("merchant.api");
        if (StrUtil.isEmpty(merchantApi)) {
            log.error("未获取到商户API");
            return null;
        }
        String merchantId = environment.getProperty("merchant.id");
        if (StrUtil.isEmpty(merchantId)) {
            log.error("未获取到商户ID");
            return null;
        }
        String merchantKey = environment.getProperty("merchant.key");
        String notifyUrl = environment.getProperty("merchant.notify-url");
        String returnUrl = environment.getProperty("merchant.return-url");
        String sign = "clientip=" + "alamp.cc"
                + "&device=" + "pc"
                + "&money=" + price
                + "&name=" + "VIP会员"
                + "&notify_url=" + notifyUrl
                + "&out_trade_no=" + orderNo
                + "&pid=" + merchantId
                + "&return_url=" + returnUrl
                + "&type=" + "alipay" + merchantKey;
        String md5Hex = MD5.create().digestHex(sign);
        HttpResponse response = HttpRequest.post(merchantApi)
                .form("pid", merchantId)
                .form("type", "alipay")
                .form("out_trade_no", orderNo)
                .form("notify_url", notifyUrl)
                .form("return_url", returnUrl)
                .form("name", "VIP会员")
                .form("money", price)
                .form("clientip", "alamp.cc")
                .form("device", "pc")
                .form("sign", md5Hex)
                .form("sign_type", "MD5")
                .execute();
        log.info("{}", response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(response.body());
        } catch (IOException e) {
            log.error("update order info error");
        }
        return null;
    }
}
