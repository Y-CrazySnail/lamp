package com.yeem.lamp.domain.service.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.objvalue.Subscription;
import com.yeem.lamp.infrastructure.persistence.repository.web.ServiceWebRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceWebDomainService {

    @Autowired
    private ServiceWebRepository serviceRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    public List<Services> listService() {
        return serviceRepository.listService();
    }

    public ServiceMonth getServiceMonth(Services services, Integer year, Integer month) {
        ServiceMonth serviceMonthParam = new ServiceMonth();
        serviceMonthParam.setServiceId(services.getId());
        serviceMonthParam.setServiceYear(year);
        serviceMonthParam.setServiceMonth(month);
        List<ServiceMonth> serviceMonthList = serviceRepository.listServiceMonth(serviceMonthParam);
        if (serviceMonthList.isEmpty()) {
            return services.generateServiceMonth(year, month);
        } else {
            return serviceMonthList.get(0);
        }
    }

    /**
     * 设置 月度服务
     *
     * @param services 服务
     * @param date     日期
     */
    public void setServiceMonth(Services services, Date date) {
        ServiceMonth serviceMonthParam = new ServiceMonth();
        serviceMonthParam.setServiceId(services.getId());
        List<ServiceMonth> serviceMonthList = serviceRepository.listServiceMonth(serviceMonthParam);
        // 赋值 当前月度服务
        services.assignCurrentServiceMonth(serviceMonthList, date);
        services.setServiceMonthList(serviceMonthList);
    }

    /**
     * 设置 服务记录
     *
     * @param serviceMonth 月度服务
     * @param date  日期
     */
    public void setServiceRecord(ServiceMonth serviceMonth, Date date) {
        if (Objects.isNull(serviceMonth) || Objects.isNull(serviceMonth.getId())) {
            return;
        }
        ServiceRecord serviceRecordParam = new ServiceRecord();
        serviceRecordParam.setServiceId(serviceMonth.getServiceId());
        serviceRecordParam.setServiceMonthId(serviceMonth.getId());
        List<ServiceRecord> serviceRecordList = serviceRepository.listServiceRecord(serviceRecordParam, date);
        serviceMonth.setServiceRecordList(serviceRecordList);
    }

    public List<Services> listByMemberId(Long memberId) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Services> servicesList = serviceRepository.listByMemberId(memberId);
        servicesList.forEach(services -> {
            this.setServiceMonth(services, current);
            this.setServiceRecord(services.getCurrentServiceMonth(), current);
        });
        return servicesList;
    }

    public void setSubscription(Services services) {
        List<Subscription> subscriptionList = serviceRepository.listSubscription();
        services.setSubscriptionList(subscriptionList);
    }

    public Services getById(Long id) {
        return serviceRepository.getServiceById(id);
    }

    public void save(Services services) {
        serviceRepository.save(services);
    }

    public void saveService(Services services) {
        serviceRepository.saveService(services);
    }

    public void saveServiceMonth(ServiceMonth serviceMonth) {
        serviceRepository.saveServiceMonth(serviceMonth);
    }

    public String clash(String uuid) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        Services services = serviceRepository.getByUUID(uuid);
        this.setServiceMonth(services, current);
        this.setSubscription(services);
        services.generateVmessNode();
        services.generateSubscriptionVmessNode();

        SpringTemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, "classpath:template");
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setTemplateLoader(templateLoader);
        Template template;
        String sub = null;
        try {
            template = configuration.getTemplate("clash.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("nodeList", services.getNodeVmessList());
            sub = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return sub;
    }

    public String v2ray(String uuid) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        Services services = serviceRepository.getByUUID(uuid);
        this.setServiceMonth(services, current);
        this.setSubscription(services);
        services.generateVmessNode();
        services.generateSubscriptionVmessNode();

        SpringTemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, "classpath:template");
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setTemplateLoader(templateLoader);
        Template template;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            template = configuration.getTemplate("v2ray.ftl");
            for (NodeVmess nodeVmess : services.getNodeVmessList()) {
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
