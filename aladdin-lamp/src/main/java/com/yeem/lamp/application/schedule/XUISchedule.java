package com.yeem.lamp.application.schedule;

import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.service.ServerAppService;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.domain.service.ServerDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XUISchedule {

    @Autowired
    private ISysTelegramService sysTelegramService;
    @Autowired
    private ServiceAppService serviceAppService;
    @Autowired
    private ServerAppService serverAppService;
    @Autowired
    private ServerDomainService serverDomainService;
    @Autowired
    private ServiceDomainService serviceDomainService;

    /**
     * 每天0点重置当日流量
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void reset() {
        try {
            log.info("begin-reset client data traffic schedule---------->");
            serverDomainService.resetClientTraffic();
            log.info("end-reset client data traffic schedule<----------");
        } catch (Exception e) {
            log.error("reset client data traffic schedule error", e);
        }
    }

    /**
     * 每月初0点生成当月服务
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateServiceMonth() {
        try {
            log.info("begin-generate service month schedule---------->");
            serviceDomainService.generateServiceMonth();
            log.info("end-generate service month schedule<----------");
        } catch (Exception e) {
            log.error("generate service month schedule error", e);
        }
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void syncRemoteService() {
        try {
            log.info("begin-sync remote service schedule---------->");
            serverAppService.syncRemoteService(null);
            log.info("end-sync remote service schedule<----------");
        } catch (Exception e) {
            log.error("sync remote service schedule error", e);
        }
    }

    @Scheduled(cron = "0 59 * * * ?")
    public void sync() {
        try {
            log.info("begin-sync data traffic schedule---------->");
            serviceAppService.syncServiceRecord();
            log.info("end-sync data traffic schedule---------->");
        } catch (Exception e) {
            log.error("sync data traffic schedule error", e);
        }
    }
}
