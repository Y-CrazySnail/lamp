package com.yeem.lamp.schedule;

import com.yeem.lamp.service.impl.XUIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XUISchedule {

    private static boolean STATUS = false;

    @Autowired
    private XUIService xuiService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void refresh() {
        try {
            if (!STATUS) {
                STATUS = true;
                log.info("开始-同步节点定时任务执行---------->");
                xuiService.sync();
                log.info("结束-同步节点定时任务执行<----------");
            }
        } catch (Exception e) {
            log.error("执行同步定时任务失败", e);
        } finally {
            STATUS = false;
        }
    }
}
