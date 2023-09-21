package com.yeem.im.schedule;

import com.yeem.im.service.ISysMailService;
import com.yeem.im.service.ISysSMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IMSchedule {

    @Autowired
    ISysMailService sysMailService;

    @Autowired
    ISysSMSService sysSmsService;
    /**
     * 发送邮件定时任务 每10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        sysMailService.send();
        sysSmsService.send();
    }
}
