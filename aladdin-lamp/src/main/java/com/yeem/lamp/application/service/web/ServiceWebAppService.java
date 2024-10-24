package com.yeem.lamp.application.service.web;

import cn.hutool.core.date.DateUtil;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.service.web.MemberWebDomainService;
import com.yeem.lamp.domain.service.web.ServerWebDomainService;
import com.yeem.lamp.domain.service.web.ServiceWebDomainService;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XClientStat;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceWebAppService {

    @Autowired
    private MemberWebDomainService memberDomainService;
    @Autowired
    private ServiceWebDomainService serviceDomainService;
    @Autowired
    private ServerWebDomainService serverDomainService;
    @Autowired
    private ResourceLoader resourceLoader;

    public List<ServiceDTO> listServiceByMemberId(Long memberId) {
        List<Services> servicesList = serviceDomainService.listByMemberId(memberId);
        return servicesList.stream().map(ServiceDTO::init).collect(Collectors.toList());
    }

    public String clash(String uuid) {
        return serviceDomainService.clash(uuid);
    }

    public String v2ray(String uuid) {
        return serviceDomainService.v2ray(uuid);
    }

    /**
     * 同步远程流量至本地
     */
    public void syncServiceRecord() {
        Date currentDate = DateUtil.beginOfDay(new Date()).toJdkDate();
        List<Server> serverList = serverDomainService.list();
        List<Services> servicesList = serviceDomainService.listService();
        servicesList.forEach(services -> {
            serviceDomainService.setServiceMonth(services, currentDate);
            if (null != services.getCurrentServiceMonth()) {
                serviceDomainService.setServiceRecord(services.getCurrentServiceMonth(), currentDate);
                services.getCurrentServiceMonth().getServiceRecordList().stream()
                        .filter(serviceRecord -> serviceRecord.getServiceDate().equals(currentDate))
                        .forEach(ServiceRecord::resetTodayBandwidth);
            }
        });
        for (Server server : serverList) {
            try {
                String region = server.getRegion();
                XUIClient xuiClient = XUIClient.init(server);
                XInbound xInbound = xuiClient.getInbound();
                List<XClientStat> xClientStatList = xInbound.getClientStats();
                for (XClientStat xClientStat : xClientStatList) {
                    log.info("region:{}, client email:{}", region, xClientStat.getEmail());
                    Long serviceId;
                    try {
                        serviceId = Long.valueOf(xClientStat.getEmail());
                    } catch (Exception e) {
                        log.error("parse service id error:{}", xClientStat.getEmail());
                        continue;
                    }
                    for (Services services : servicesList) {
                        if (services.getId().equals(serviceId)) {
                            ServiceMonth serviceMonth = services.getCurrentServiceMonth();
                            List<ServiceRecord> serviceRecordList = serviceMonth.getServiceRecordList();
                            ServiceRecord serviceRecord = null;
                            for (ServiceRecord record : serviceRecordList) {
                                if (region.equals(record.getRegion()) && currentDate.equals(record.getServiceDate())) {
                                    serviceRecord = record;
                                }
                            }
                            if (null == serviceRecord) {
                                serviceRecord = serviceMonth.generateServiceRecord(currentDate, region);
                                serviceRecord.addBandwidthUp(xClientStat.getUp() * server.getMultiplyingPower());
                                serviceRecord.addBandwidthDown(xClientStat.getDown() * server.getMultiplyingPower());
                                serviceMonth.getServiceRecordList().add(serviceRecord);
                            } else {
                                serviceRecord.addBandwidthUp(xClientStat.getUp() * server.getMultiplyingPower());
                                serviceRecord.addBandwidthDown(xClientStat.getDown() * server.getMultiplyingPower());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("server:{}-{}, sync Service Record error:", server.getApiIp(), server.getRegion(), e);
            }
        }
        serviceDomainService.syncService(servicesList);
    }
}
