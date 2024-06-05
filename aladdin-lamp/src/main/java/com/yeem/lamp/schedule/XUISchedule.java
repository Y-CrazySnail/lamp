package com.yeem.lamp.schedule;

import com.yeem.im.service.ISysTelegramService;
import com.yeem.lamp.infrastructure.persistence.repository.XUIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XUISchedule {

    @Autowired
    private XUIService xuiService;
    @Autowired
    private ISysTelegramService sysTelegramService;

//    @Scheduled(cron = "0 0/10 * * * ?")
    public void refresh() {
        try {
            log.info("开始-同步节点定时任务执行---------->");
            xuiService.sync();
            log.info("结束-同步节点定时任务执行<----------");
        } catch (Exception e) {
            log.error("执行同步定时任务失败", e);
        }
    }
}
