package com.yeem.common.im.schedule;

import com.yeem.common.im.service.ISysMailService;
import com.yeem.common.im.service.ISysSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class IMSchedule {

    @Autowired
    ISysMailService sysMailService;

    @Autowired
    ISysSmsService sysSmsService;
    /**
     * 发送邮件定时任务 每10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        log.info("send mail start:{}", LocalDateTime.now());
        sysMailService.send();
        sysSmsService.send();
    }
}
