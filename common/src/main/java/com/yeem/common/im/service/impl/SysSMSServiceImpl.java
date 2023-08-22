package com.yeem.common.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloudapi.common.Credential;


//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

// 导入对应SMS模块的client
import com.tencentcloudapi.sms.v20210111.SmsClient;

// 导入要请求接口对应的request response类
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import com.yeem.common.im.dto.SysSMSSendDTO;
import com.yeem.common.im.entity.SysSMS;
import com.yeem.common.im.entity.SysTemplate;
import com.yeem.common.im.mapper.SysSmsMapper;
import com.yeem.common.im.service.ISysSMSService;
import com.yeem.common.utils.FreeMakerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysSMSServiceImpl extends ServiceImpl<SysSmsMapper, SysSMS> implements ISysSMSService {
    @Autowired
    private Environment environment;

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
        sysSMS.setSignName(sysTemplate.getSignName());
        sysSMS.setExtendCode(sysSMSSendDTO.getExtendCode());
        sysSMS.setSenderId(sysSMSSendDTO.getSenderId());
        sysSMS.setSessionContext(sysSMSSendDTO.getSessionContext());
        sysSMS.setBusinessId(sysSMSSendDTO.getBusinessId());
        sysSMS.setBusinessName(sysSMSSendDTO.getTemplateName());
        sysSMS.setTemplateId(sysTemplate.getTemplateId());
        if (!StringUtils.isEmpty(sysSMS.getTimingTime())) {
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
            // SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi
            Credential cred = new Credential(environment.getProperty("sms.tencentcloud-secret-id"), environment.getProperty("sms.tencentcloud-secret-key"));
            // httpProfile.setProxyHost("真实代理ip");
            // httpProfile.setProxyPort(真实代理端口);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            /* 指定接入地域域名，默认就近地域接入域名为 sms.tencentcloudapi.com ，也支持指定地域域名访问，例如广州地域的域名为 sms.ap-guangzhou.tencentcloudapi.com */
            httpProfile.setEndpoint(environment.getProperty("sms.endpoint"));
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
            SmsClient client = new SmsClient(cred, environment.getProperty("sms.region"), clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            /* 填充请求参数,这里request对象的成员变量即对应接口的入参
             * 短信控制台: https://console.cloud.tencent.com/smsv2
             * 腾讯云短信小助手: https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81 */

            // 应用 ID 可前往 [短信控制台](https://console.cloud.tencent.com/smsv2/app-manage) 查看
            String sdkAppId = environment.getProperty("sms.sdk-app-id");
            req.setSmsSdkAppId(sdkAppId);

            // 签名信息可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-sign) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-sign) 的签名管理查看
            String signName = sysSMS.getSignName();
            req.setSignName(signName);

            // 模板 ID 可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-template) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-template) 的正文模板管理查看
            String templateId = sysSMS.getTemplateId();
            req.setTemplateId(templateId);

            /* 模板参数: 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致，若无模板参数，则设置为空 */
            Map<String, String> replaceMap = new ObjectMapper().readValue(sysSMS.getParam(),
                    new TypeReference<Map<String, String>>() {
                    });
            String[] templateParamSet = new String[replaceMap.size()];
            for (int i = 1; i <= replaceMap.size(); i++) {
                templateParamSet[i - 1] = replaceMap.get("param" + i);
            }
            req.setTemplateParamSet(templateParamSet);

            /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]最多不要超过200个手机号 */
            String[] splitPhone = sysSMS.getToPhone().split(",");
            for (String phone : splitPhone) {
                if (!phone.startsWith("+86")){
                    phone="+86"+phone;
                }
            }
            req.setPhoneNumberSet(splitPhone);

            /* 用户的 session 内容（无需要可忽略）: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            String sessionContext = sysSMS.getSessionContext();
            req.setSessionContext(sessionContext);

            /* 短信码号扩展号（无需要可忽略）: 默认未开通，如需开通请联系 [腾讯云短信小助手] */
            String extendCode = sysSMS.getExtendCode();
            req.setExtendCode(extendCode);

            /* 国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。注：月度使用量达到指定量级可申请独立 SenderId 使用，详情请联系 [腾讯云短信小助手](https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81)。*/
            String senderid = sysSMS.getSenderId();
            req.setSenderId(senderid);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            // 输出json格式的字符串回包
            // 也可以取出单个值，你可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
            // System.out.println(res.getRequestId());
            System.out.println(SendSmsResponse.toJsonString(res));

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
}
