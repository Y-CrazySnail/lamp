package com.yeem.lamp.application.schedule;

import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.service.ServerAppService;
import com.yeem.lamp.application.service.ServiceAppService;
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
    private ServerAppService serverAppService;
    @Autowired
    private ServiceAppService serviceAppService;

    @Autowired
    private ServiceDomainService serviceDomainService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void reset() {
        try {
            log.info("begin-reset client data traffic schedule---------->");
            serviceDomainService.resetClientTraffic();
            log.info("end-reset client data traffic schedule<----------");
        } catch (Exception e) {
            log.error("reset client data traffic schedule error", e);
        }
    }

    @Scheduled(cron = "0 59 * * * ?")
    public void sync() {
        try {
            log.info("begin-sync data traffic schedule---------->");
            serviceDomainService.syncDataTraffic();
            log.info("end-sync data traffic schedule---------->");
        } catch (Exception e) {
            log.error("sync data traffic schedule error", e);
        }
    }
}
