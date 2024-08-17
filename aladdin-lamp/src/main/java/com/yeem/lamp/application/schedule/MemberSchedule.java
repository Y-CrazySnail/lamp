package com.yeem.lamp.application.schedule;

import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.application.service.ServerAppService;
import com.yeem.lamp.application.service.ServiceAppService;
import com.yeem.lamp.domain.service.MemberDomainService;
import com.yeem.lamp.domain.service.ServerDomainService;
import com.yeem.lamp.domain.service.ServiceDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemberSchedule {

    @Autowired
    private MemberDomainService memberDomainService;

    /**
     * 每小时重置当日流量
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void syncMemberLevel() {
        try {
            log.info("begin-sync member level schedule---------->");
//            memberDomainService.resetClientTraffic();
            log.info("end-sync member level schedule<----------");
        } catch (Exception e) {
            log.error("reset sync member level schedule error", e);
        }
    }
}
