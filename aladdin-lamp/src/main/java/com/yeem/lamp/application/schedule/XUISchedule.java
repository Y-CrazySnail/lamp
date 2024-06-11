package com.yeem.lamp.application.schedule;

import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.service.ServerAppService;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.application.service.XUIAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XUISchedule {

    @Autowired
    private XUIAppService xuiAppService;
    @Autowired
    private ISysTelegramService sysTelegramService;
    @Autowired
    private ServerAppService serverAppService;
    @Autowired
    private ServiceAppService serviceAppService;

    @Scheduled(cron = "0 3 0 * * ?")
    public void reset() {
        try {
            log.info("begin-reset node schedule---------->");
            serviceAppService.syncStatus();
            serverAppService.syncNode();
            xuiAppService.reset();
            log.info("end-reset node schedule<----------");
        } catch (Exception e) {
            log.error("reset node schedule error", e);
        }
    }

    @Scheduled(cron = "0 59 * * * ?")
    public void sync() {
        try {
            log.info("begin-sync remote data traffic---------->");
            xuiAppService.syncRemoteDataTraffic();
            log.info("end-sync remote data traffic---------->");

            log.info("begin-sync local service status---------->");
            serviceAppService.syncStatus();
            log.info("end-sync local service status---------->");

            log.info("begin-sync local node status---------->");
            serverAppService.syncNode();
            log.info("end-sync local node status---------->");

            log.info("begin-sync remote node status---------->");
            xuiAppService.syncRemoteNode();
            log.info("end-sync remote node status---------->");
        } catch (Exception e) {
            log.error("reset node schedule error", e);
        }
    }
}
