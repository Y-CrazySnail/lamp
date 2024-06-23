package com.yeem.lamp.domain.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.objvalue.Subscription;
import com.yeem.lamp.domain.repository.ServiceRepository;
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
public class ServiceDomainService {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    public List<Services> listByMemberId(Long memberId) {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Services> servicesList = serviceRepository.listByMemberId(memberId);
        servicesList.forEach(services -> {
            this.setServiceMonth(services, current);
            this.setServiceRecord(services.getCurrentServiceMonth(), current);
        });
        return servicesList;
    }

    public List<Server> listServer() {
        return serviceRepository.listServer();
    }

    public List<Services> listService() {
        return serviceRepository.listService(new Services());
    }

    public void setServiceMonth(Services services, Date current) {
        ServiceMonth serviceMonthParam = new ServiceMonth();
        serviceMonthParam.setServiceId(services.getId());
        if (null != current) {
            serviceMonthParam.setServiceYear(DateUtil.year(current));
            serviceMonthParam.setServiceMonth(DateUtil.month(current) + 1);
        }
        List<ServiceMonth> serviceMonthList = serviceRepository.listServiceMonth(serviceMonthParam);
        if (null != current) {
            if (!serviceMonthList.isEmpty()) {
                services.setCurrentServiceMonth(serviceMonthList.get(0));
                services.setServiceMonthList(serviceMonthList);
            }
        } else {
            services.setServiceMonthList(serviceMonthList);
        }
    }

    public void setServiceRecord(ServiceMonth serviceMonth, Date current) {
        ServiceRecord serviceRecordParam = new ServiceRecord();
        serviceRecordParam.setServiceId(serviceMonth.getServiceId());
        serviceRecordParam.setServiceMonthId(serviceMonth.getId());
        List<ServiceRecord> serviceRecordList = serviceRepository.listServiceRecord(serviceRecordParam, current);
        serviceMonth.setServiceRecordList(serviceRecordList);
    }

    public void setSubscription(Services services) {
        List<Subscription> subscriptionList = serviceRepository.listSubscription();
        services.setSubscriptionList(subscriptionList);
    }

    public Services getById(Long id) {
        return serviceRepository.getServiceById(id);
    }

    public IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email) {
        return serviceRepository.pages(current, size, memberId, status, wechat, email);
    }

    public void updateById(Services services) {
        serviceRepository.updateById(services);
    }

    public void save(Services services) {
        serviceRepository.save(services);
    }

    public void removeById(Long id) {
        serviceRepository.removeById(id);
    }

    /**
     * 生成当月服务
     */
    public void generateServiceMonth() {
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        Integer year = DateUtil.year(current);
        Integer month = DateUtil.month(current) + 1;
        log.info("generate service month, year:{} month:{}", year, month);
        List<Services> servicesList = serviceRepository.listService(new Services());
        ServiceMonth serviceMonthParam = new ServiceMonth();
        serviceMonthParam.setServiceYear(year);
        serviceMonthParam.setServiceMonth(month);
        List<ServiceMonth> serviceMonthList = serviceRepository.listServiceMonth(serviceMonthParam);
        Set<Long> serviceIdSet = serviceMonthList.stream().map(ServiceMonth::getServiceId).collect(Collectors.toSet());
        for (Services services : servicesList) {
            if (!services.isValid()) {
                log.info("service has expired:{}", services.getEndDate());
                continue;
            }
            if (serviceIdSet.contains(services.getId())) {
                log.info("service month has exist, serviceId:{}", services.getId());
                continue;
            }
            ServiceMonth serviceMonth = services.generateServiceMonth(year, month);
            services.setCurrentServiceMonth(serviceMonth);
            log.info("generate service month, service id:{}", services.getId());
            serviceRepository.save(services);
        }
    }

    public void syncService(List<Services> servicesList) {
        for (Services services : servicesList) {
            ServiceMonth serviceMonth = services.getCurrentServiceMonth();
            if (null == serviceMonth) {
                continue;
            } else {
                serviceMonth.syncBandwidth();
            }
            serviceRepository.save(services);
        }
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
                        .append(cn.hutool.core.codec.Base64.encode(FreeMarkerTemplateUtils.processTemplateIntoString(template, nodeVmess)))
                        .append("\n");
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return Base64.encode(stringBuilder.toString());
    }
}
