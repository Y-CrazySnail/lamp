package com.yeem.car_film_saas.schedule;

import com.yeem.car_film_saas.service.ISysIMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class IMSchedule {

    @Autowired
    private ISysIMService sysIMService;

    /**
     * 发送邮件定时任务 每10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        log.info("send mail start:{}", LocalDateTime.now());
        // todo 定时任务 定时扫描
    }
}
