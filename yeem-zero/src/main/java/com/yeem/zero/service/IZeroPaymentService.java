package com.yeem.zero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.yeem.zero.entity.ZeroOrder;
import com.yeem.zero.entity.ZeroPayment;

public interface IZeroPaymentService extends IService<ZeroPayment> {
    ZeroPayment getByOrderNo(String orderNo);
    PrepayWithRequestPaymentResponse wechatPrepay(ZeroOrder zeroOrder);
    PrepayWithRequestPaymentResponse wechatPrepay(String openId, ZeroOrder zeroOrder);
    void callback(String timestamp, String nonce, String serialNo, String signature, ObjectNode objectNode);
}
