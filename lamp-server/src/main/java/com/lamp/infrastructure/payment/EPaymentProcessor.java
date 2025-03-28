package com.lamp.infrastructure.payment;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.common.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
@Component
public class EPaymentProcessor {

    @Autowired
    private DictService dictService;

    public JsonNode prepay(BigDecimal price, String orderNo) {
        String merchantApi = dictService.getValueByTypeAndKey("merchant", "api", "");
        if (StrUtil.isEmpty(merchantApi)) {
            log.error("未获取到商户API");
            return null;
        }
        String merchantId = dictService.getValueByTypeAndKey("merchant", "id", "");
        if (StrUtil.isEmpty(merchantId)) {
            log.error("未获取到商户ID");
            return null;
        }
        String merchantKey = dictService.getValueByTypeAndKey("merchant", "key", "");
        String notifyUrl = dictService.getValueByTypeAndKey("merchant", "notify-url", "");
        String returnUrl = dictService.getValueByTypeAndKey("merchant", "return-url", "");
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
