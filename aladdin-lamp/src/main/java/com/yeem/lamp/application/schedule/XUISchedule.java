package com.yeem.lamp.application.schedule;

import com.yeem.lamp.application.service.web.ServerWebAppService;
import com.yeem.lamp.application.service.web.ServiceWebAppService;
import com.yeem.lamp.domain.service.web.ServerWebDomainService;
import com.yeem.lamp.domain.service.web.ServiceWebDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XUISchedule {

    @Autowired
    private ServiceWebAppService serviceAppService;
    @Autowired
    private ServerWebAppService serverAppService;
    @Autowired
    private ServerWebDomainService serverDomainService;
    @Autowired
    private ServiceWebDomainService serviceDomainService;

    /**
     * 每天0点重置当日流量
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void reset() {
        try {
            serviceAppService.sync();
        } catch (Exception e) {
            log.error("reset client data traffic schedule error", e);
        }
    }
}
