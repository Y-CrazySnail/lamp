package com.yeem.lamp.schedule;

import com.yeem.lamp.service.impl.XUIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XUISchedule {

    @Autowired
    private XUIService xuiService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void refresh() {
        log.info("开始-同步节点定时任务执行---------->");
        xuiService.sync();
        log.info("结束-同步节点定时任务执行<----------");
    }
}
