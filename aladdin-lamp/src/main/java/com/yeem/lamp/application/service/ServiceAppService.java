package com.yeem.lamp.application.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.service.MemberDomainService;
import com.yeem.lamp.domain.service.ServerDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XClientStat;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceAppService {

    @Autowired
    private MemberDomainService memberDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;
    @Autowired
    private ServerDomainService serverDomainService;
    @Autowired
    private ResourceLoader resourceLoader;

    public ServiceDTO getServiceById(Long id) {
        Services services = serviceDomainService.getById(id);
        return new ServiceDTO(services);
    }

    public List<ServiceDTO> listServiceByMemberId(Long memberId) {
        List<Services> servicesList = serviceDomainService.listByMemberId(memberId);
        return servicesList.stream().map(ServiceDTO::new).collect(Collectors.toList());
    }

    public IPage<ServiceDTO> pageService(int current, int size, Long memberId, String status, String wechat, String email) {
        IPage<Services> page = serviceDomainService.pages(current, size, memberId, status, wechat, email);
        IPage<ServiceDTO> pageDTO = new Page<>();
        pageDTO.setPages(page.getPages());
        pageDTO.setTotal(page.getTotal());
        pageDTO.setSize(page.getSize());
        pageDTO.setCurrent(page.getCurrent());
        pageDTO.setRecords(page.getRecords().stream().map(ServiceDTO::new).collect(Collectors.toList()));
        return pageDTO;
    }

    public void saveService(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.save(services);
    }

    public void updateServiceById(ServiceDTO serviceDTO) {
        Services services = serviceDTO.convertService();
        serviceDomainService.updateById(services);
    }

    public void removeServiceById(Long id) {
        serviceDomainService.removeById(id);
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
                services.getCurrentServiceMonth().getServiceRecordList().forEach(ServiceRecord::resetTodayBandwidth);
            }
        });
        for (Server server : serverList) {
            String region = server.getRegion();
            XUIClient xuiClient = XUIClient.init(server);
            XInbound xInbound = xuiClient.getInbound();
            List<XClientStat> xClientStatList = xInbound.getClientStats();
            for (XClientStat xClientStat : xClientStatList) {
                Long serviceId = Long.valueOf(xClientStat.getEmail());
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
        }
        serviceDomainService.syncService(servicesList);
    }
}
