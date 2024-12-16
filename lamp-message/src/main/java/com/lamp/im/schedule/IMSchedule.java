package com.lamp.im.schedule;

import com.lamp.im.service.ISysMailService;
import com.lamp.im.service.ISysSMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void schedule() {
        sysMailService.send();
        sysSmsService.send();
    }
}
