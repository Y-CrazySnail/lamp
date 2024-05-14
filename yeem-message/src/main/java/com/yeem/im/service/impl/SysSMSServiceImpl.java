package com.yeem.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloudapi.common.Credential;

import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import com.yeem.im.dto.SysSMSSendDTO;
import com.yeem.im.entity.SysSMS;
import com.yeem.im.entity.SysTemplate;
import com.yeem.im.mapper.SysSmsMapper;
import com.yeem.im.service.ISysSMSService;
import com.yeem.common.utils.FreeMakerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysSMSServiceImpl extends ServiceImpl<SysSmsMapper, SysSMS> implements ISysSMSService {
    @Value("${tencent.secret-id:tencent_secret_id}")
    private String TENCENT_SECRET_ID;

    @Value("${tencent.secret-key:tencent_secret_key}")
    private String TENCENT_SECRET_KEY;

    @Value("${tencent.sms.end-point:tencent_sms_end_point}")
    private String TENCENT_SMS_END_POINT;

    @Value("${tencent.sms.region:tencent_sms_region}")
    private String TENCENT_SMS_REGION;

    @Value(value = "${tencent.sms.sdk-app-id:tencent_sms_sdk_app_id}")
    private String TENCENT_SMS_SDK_APP_ID;

    @Autowired
    private SysSmsMapper sysSmsMapper;

    @Override
    public List<SysSMS> getTodo() {
        return sysSmsMapper.getTodo();
    }

    @Override
    public void save(SysSMSSendDTO sysSMSSendDTO, SysTemplate sysTemplate) {
        ObjectMapper objectMapper = new ObjectMapper();
        SysSMS sysSMS = new SysSMS();
        sysSMS.setToPhone(sysSMSSendDTO.getPhone());
        sysSMS.setTimingTime(sysSMSSendDTO.getTimingTime());
        try {
            sysSMS.setParam(objectMapper.writeValueAsString(sysSMSSendDTO.getReplaceMap()));
        } catch (JsonProcessingException e) {
            log.error("error");
        }
        sysSMS.setContent(FreeMakerUtils.getContent(sysTemplate.getContent(), sysSMSSendDTO.getReplaceMap()));
        sysSMS.setTemplateId(sysSMSSendDTO.getTemplateName());
        sysSMS.setSignName(sysTemplate.getSmsSignName());
        sysSMS.setExtendCode(sysSMSSendDTO.getExtendCode());
        sysSMS.setSenderId(sysSMSSendDTO.getSenderId());
        sysSMS.setSessionContext(sysSMSSendDTO.getSessionContext());
        sysSMS.setBusinessId(sysSMSSendDTO.getBusinessId());
        sysSMS.setBusinessName(sysSMSSendDTO.getTemplateName());
        sysSMS.setTemplateId(sysTemplate.getSmsTemplateId());
        if (sysSMS.getTimingTime() != null) {
            sysSMS.setTimingFlag(1);
            sysSMS.setTimingTime(sysSMSSendDTO.getTimingTime());
            sysSmsMapper.insert(sysSMS);
        } else {
            sysSMS.setTimingFlag(0);
            sysSmsMapper.insert(sysSMS);
            this.send(sysSMS);
        }
    }

    @Override
    public void send() {
        List<SysSMS> todoSMS = this.getTodo();
        for (SysSMS sysSMS : todoSMS) {
            send(sysSMS);
        }
    }

    @Override
    public void send(SysSMS sysSMS) {
        try {
            SmsClient client = getSmsClient();
            SendSmsRequest request = new SendSmsRequest();
            request.setSmsSdkAppId(TENCENT_SMS_SDK_APP_ID);
            String signName = sysSMS.getSignName();
            request.setSignName(signName);
            String templateId = sysSMS.getTemplateId();
            request.setTemplateId(templateId);
            Map<String, String> replaceMap = new ObjectMapper().readValue(sysSMS.getParam(),
                    new TypeReference<Map<String, String>>() {
                    });
            String[] templateParamSet = new String[replaceMap.size()];
            for (int i = 1; i <= replaceMap.size(); i++) {
                templateParamSet[i - 1] = replaceMap.get("param" + i);
            }
            request.setTemplateParamSet(templateParamSet);
            String[] splitPhone = sysSMS.getToPhone().split(",");
            for (int i = 0; i < splitPhone.length; i++) {
                if (!splitPhone[i].startsWith("+86")) {
                    splitPhone[i] = "+86" + splitPhone[i];
                }
            }
            request.setPhoneNumberSet(splitPhone);

            /* 用户的 session 内容（无需要可忽略）: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            String sessionContext = sysSMS.getSessionContext();
            request.setSessionContext(sessionContext);
            /* 短信码号扩展号（无需要可忽略）: 默认未开通，如需开通请联系 [腾讯云短信小助手] */
            String extendCode = sysSMS.getExtendCode();
            request.setExtendCode(extendCode);
            /* 国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。注：月度使用量达到指定量级可申请独立 SenderId 使用，详情请联系 [腾讯云短信小助手](https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81)。*/
            String senderid = sysSMS.getSenderId();
            request.setSenderId(senderid);
            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(request);
            log.info("腾讯云短信响应内容：{}", SendSmsResponse.toJsonString(res));
            for (SendStatus sendStatus : res.getSendStatusSet()) {
                if ("Ok".equals(sendStatus.getCode())) {
                    sysSMS.setState(1);
                    sysSMS.setSendTime(new Date());
                } else {
                    sysSMS.setState(9);
                    sysSMS.setException(sendStatus.getMessage());
                    log.error("send sms error sendStatus->NoOK ");
                }
            }
        } catch (Exception e) {
            sysSMS.setState(9);
            sysSMS.setException(e.toString());
            log.error("send sms error", e);
        } finally {
            sysSmsMapper.updateById(sysSMS);
        }
    }

    private SmsClient getSmsClient() {
        Credential credential = new Credential(TENCENT_SECRET_ID, TENCENT_SECRET_KEY);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint(TENCENT_SMS_END_POINT);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(credential, TENCENT_SMS_REGION, clientProfile);
    }
}
