package com.lamp.application.schedule;

import com.lamp.application.service.web.ServerWebAppService;
import com.lamp.application.service.web.ServiceWebAppService;
import com.lamp.domain.service.web.ServerWebDomainService;
import com.lamp.domain.service.web.ServiceWebDomainService;
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
     * 每小时执行
     */
//    @Scheduled(cron = "0 0 0/4 * * ?")
    public void reset() {
        try {
            serviceAppService.sync();
        } catch (Exception e) {
            log.error("sync error", e);
        }
    }
}
