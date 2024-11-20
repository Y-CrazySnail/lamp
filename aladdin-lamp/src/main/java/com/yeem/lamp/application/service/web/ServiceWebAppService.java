package com.yeem.lamp.application.service.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yeem.lamp.application.dto.ServiceDTO;
import com.yeem.lamp.domain.entity.Member;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.service.web.MemberWebDomainService;
import com.yeem.lamp.domain.service.web.ServerWebDomainService;
import com.yeem.lamp.domain.service.web.ServiceWebDomainService;
import com.yeem.lamp.infrastructure.message.TelegramMessage;
import com.yeem.lamp.infrastructure.x.XUIClient;
import com.yeem.lamp.infrastructure.x.model.XClientSetting;
import com.yeem.lamp.infrastructure.x.model.XClientStat;
import com.yeem.lamp.infrastructure.x.model.XInbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    @Autowired
    private TelegramMessage telegramMessage;

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

    public void sync() {
        Date current = new Date();
        // 获取服务器列表
        List<Server> serverList = serverDomainService.list();
        // 获取服务列表
        List<Services> servicesList = serviceDomainService.listService();
        // 判断服务列表是否为空，为空时不处理
        if (Objects.isNull(servicesList) || servicesList.isEmpty()) {
            log.info("同步服务列表为空，跳过不处理");
            return;
        }
        for (Server server : serverList) {
            log.info("开始同步服务器流量：{}---{}", server.getId(), server.getRegion());
            try {
                Date lastSyncDate = new Date();
                XUIClient xuiClient = XUIClient.init(server);
                XInbound xInbound = xuiClient.getInbound();
                if (Objects.isNull(xInbound)) {
                    log.info("当前服务器新服务器，初始化配置......");
                    Map<Long, String> servicesMap = servicesList.stream()
                            .collect(Collectors.toMap(Services::getId, Services::getUuid));
                    xuiClient.addVmessInbound(server.getId(), server.getInboundPort(), servicesMap);
                    xInbound = xuiClient.getInbound();
                }
                List<XClientStat> xClientStatList = xInbound.getClientStats();
                Map<String, XClientStat> xClientStatMap = xClientStatList.stream()
                        .collect(Collectors.toMap(XClientStat::getEmail, xClientStat -> xClientStat));
                for (Services services : servicesList) {
                    try {
                        log.info("开始同步服务流量：{}---{}", services.getId(), services.getUuid());
                        XClientStat xClientStat = xClientStatMap.get(String.valueOf(services.getId()));
                        if (Objects.isNull(xClientStat)) {
                            log.info("服务：{}未使用流量，忽略处理", services.getId());
                            continue;
                        }
                        // 同步流量至本地
                        Date lastSyncTime = services.getLastSyncTime();
                        if (null == lastSyncTime || lastSyncTime.before(DateUtil.beginOfDay(DateUtil.yesterday()))) {
                            lastSyncTime = current;
                        }
                        Integer lastYear = DateUtil.year(lastSyncTime);
                        Integer lastMonth = DateUtil.month(lastSyncTime) + 1;
                        ServiceMonth lastServiceMonth = serviceDomainService.getServiceMonth(services, lastYear, lastMonth);
                        serviceDomainService.setServiceRecord(lastServiceMonth, lastSyncTime);
                        // 重置同步日期的流量记录
                        lastServiceMonth.resetRecordBandwidth(lastSyncTime);
                        String region = server.getRegion();
                        // 过滤当前地区、当天的服务记录
                        ServiceRecord lastServiceRecord = null;
                        if (!Objects.isNull(lastServiceMonth.getServiceRecordList())) {
                            for (ServiceRecord serviceRecord : lastServiceMonth.getServiceRecordList()) {
                                if (Objects.equals(region, serviceRecord.getRegion())
                                        && DateUtil.isSameDay(lastSyncTime, serviceRecord.getServiceDate())) {
                                    lastServiceRecord = serviceRecord;
                                }
                            }
                        }
                        // 当前地区、当天服务记录为空
                        if (Objects.isNull(lastServiceRecord)) {
                            lastServiceRecord = lastServiceMonth.generateServiceRecord(lastSyncTime, region);
                            lastServiceRecord.addBandwidthUp(xClientStat.getUp() * server.getMultiplyingPower());
                            lastServiceRecord.addBandwidthDown(xClientStat.getDown() * server.getMultiplyingPower());
                            lastServiceMonth.getServiceRecordList().add(lastServiceRecord);
                        } else {
                            lastServiceRecord.addBandwidthUp(xClientStat.getUp() * server.getMultiplyingPower());
                            lastServiceRecord.addBandwidthDown(xClientStat.getDown() * server.getMultiplyingPower());
                        }
                        services.setLastSyncTime(current);
                        serviceDomainService.saveService(services);
                        serviceDomainService.saveServiceMonth(lastServiceMonth);
                    } catch (Exception e) {
                        log.error("同步服务流量异常：{}---{}", services.getId(), services.getUuid(), e);
                    }
                    log.info("结束同步服务流量：{}---{}", services.getId(), services.getUuid());
                }
                if (DateUtil.hour(current, true) == 0) {
                    log.info("已跨日期，重置流量");
                    xuiClient.resetClientTraffic(xInbound.getId());
                }
            } catch (Exception e) {
                log.error("同步服务器流量异常：{}---{}", server.getId(), server.getRegion(), e);
            }
            log.info("结束同步服务器流量：{}---{}", server.getId(), server.getRegion());
        }
        for (Server server : serverList) {
            log.info("开始同步服务器节点：{}---{}", server.getId(), server.getRegion());
            try {
                XUIClient xuiClient = XUIClient.init(server);
                XInbound xInbound = xuiClient.getInbound();
                xInbound.initClientSettings();
                List<XClientStat> xClientStatList = xInbound.getClientStats();
                Map<String, XClientStat> xClientStatMap = xClientStatList.stream()
                        .collect(Collectors.toMap(XClientStat::getEmail, xClientStat -> xClientStat));
                List<XClientSetting> xClientSettingList = xInbound.getClientSettings();
                Map<String, XClientSetting> xClientSettingMap = xClientSettingList.stream()
                        .collect(Collectors.toMap(XClientSetting::getEmail, xClientSetting -> xClientSetting));
                for (Services services : servicesList) {
                    Member member = memberDomainService.getById(services.getMemberId());
                    String serviceIdStr = String.valueOf(services.getId());
                    log.info("开始同步服务节点：{}---{}", services.getId(), services.getUuid());
                    if (services.isValid()) {
                        Date currentDate = DateUtil.beginOfDay(current).toJdkDate();
                        Integer year = DateUtil.year(currentDate);
                        Integer month = DateUtil.month(currentDate) + 1;
                        ServiceMonth serviceMonth = serviceDomainService.getServiceMonth(services, year, month);
                        serviceDomainService.setServiceRecord(serviceMonth, currentDate);
                        if (serviceMonth.isValid()) {
                            if (!xClientStatMap.containsKey(serviceIdStr)) {
                                telegramMessage.sendRecoverNotice(member, server);
                                log.info("当前服务：{}---{}在远程服务器不存在，新建远程节点", services.getId(), services.getUuid());
                                xuiClient.addVmessClient(xInbound, services.getUuid(), services.getId());
                            }
                        } else {
                            if (xClientStatMap.containsKey(serviceIdStr)) {
                                telegramMessage.sendExcessNotice(member, server);
                                log.warn("当前服务已超量，删除远程节点：{}---{}", services.getId(), services.getUuid());
                                xuiClient.delVmessClient(xInbound.getId(), services.getUuid());
                            }
                        }
                        serviceDomainService.saveServiceMonth(serviceMonth);
                    } else {
                        if (xClientStatMap.containsKey(serviceIdStr)) {
                            telegramMessage.sendExpireNotice(member, server);
                            log.warn("当前服务已过期，删除远程节点：{}---{}", services.getId(), services.getUuid());
                            xuiClient.delVmessClient(xInbound.getId(), services.getUuid());
                        }
                    }
                    xClientStatMap.remove(serviceIdStr);
                    log.info("结束同步服务节点：{}---{}", services.getId(), services.getUuid());
                }
                xClientStatMap.forEach((serviceId, xClientStat) -> {
                    log.warn("当前服务不存在，删除远程节点：{}", serviceId);
                    xuiClient.delVmessClient(xInbound.getId(), xClientSettingMap.get(serviceId).getId());
                });
            } catch (Exception e) {
                log.info("同步服务器节点异常：{}---{}", server.getId(), server.getRegion(), e);
            }
            log.info("结束同步服务器节点：{}---{}", server.getId(), server.getRegion());
        }
    }
}
