package com.lamp.service.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.entity.LampService;
import com.lamp.entity.LampServiceMonth;
import com.lamp.entity.LampSubscription;
import com.lamp.mapper.LampSubscriptionMapper;
import com.lamp.xui.subscription.NodeVmess;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.util.*;

@Service
public class LampSubscriptionService extends ServiceImpl<LampSubscriptionMapper, LampSubscription> {

    @Autowired
    private LampSubscriptionMapper subscriptionMapper;

    @Autowired
    private LampServiceService serviceService;

    @Autowired
    private LampServiceMonthService serviceMonthService;

    @Autowired
    private ResourceLoader resourceLoader;

    public String clash(String uuid) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        LampService service = serviceService.getByUUID(uuid);
        if (service == null) {
            return null;
        }
        serviceMonthService.setServiceMonth(service, current);
        List<LampSubscription> subscriptionList = list();
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        for (LampServiceMonth serviceMonth : service.getServiceMonthList()) {
            serviceMonth.calculateClientTraffic();
            nodeVmessList.addAll(NodeVmess.generateVmessNode(subscriptionList, serviceMonth.getUuid()));
            nodeVmessList.addAll(NodeVmess.generateSubscriptionVmessNode(serviceMonth));
        }
        SpringTemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, "classpath:template");
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setTemplateLoader(templateLoader);
        Template template;
        String sub = null;
        try {
            template = configuration.getTemplate("clash.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("nodeList", nodeVmessList);
            sub = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return sub;
    }

    public String v2ray(String uuid) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        LampService service = serviceService.getByUUID(uuid);
        if (service == null) {
            return null;
        }
        serviceMonthService.setServiceMonth(service, current);
        List<LampSubscription> subscriptionList = list();
        List<NodeVmess> nodeVmessList = new ArrayList<>();
        for (LampServiceMonth serviceMonth : service.getServiceMonthList()) {
            serviceMonth.calculateClientTraffic();
            nodeVmessList.addAll(NodeVmess.generateVmessNode(subscriptionList, serviceMonth.getUuid()));
            nodeVmessList.addAll(NodeVmess.generateSubscriptionVmessNode(serviceMonth));
        }

        SpringTemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, "classpath:template");
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setTemplateLoader(templateLoader);
        Template template;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            template = configuration.getTemplate("v2ray.ftl");
            for (NodeVmess nodeVmess : nodeVmessList) {
                stringBuilder.append("vmess://")
                        .append(Base64.encode(FreeMarkerTemplateUtils.processTemplateIntoString(template, nodeVmess)))
                        .append("\n");
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return Base64.encode(stringBuilder.toString());
    }
}
