package com.yeem.one.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 */
@Slf4j
@Component
@EnableScheduling
public class ScheduleTask {

    /**
     * 关闭订单定时任务
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void testScheduleTask() {
        log.debug("scan for order waiting to be closed：{}", new Date());
    }
}