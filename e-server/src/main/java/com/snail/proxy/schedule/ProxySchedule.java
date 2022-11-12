package com.snail.proxy.schedule;

import com.snail.proxy.service.IMemberService;
import com.snail.proxy.service.INodeService;
import com.snail.proxy.service.IServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ProxySchedule {

    @Autowired
    private IServerService serverService;

    @Autowired
    private INodeService nodeService;

    @Autowired
    private IMemberService memberService;

    /**
     * 自动生成节点 定时任务
     */
    @Scheduled(cron = "0 5 1 * * ?")
    public void schedule() {
        log.info("generate node start:{}", LocalDateTime.now());
        nodeService.generateNode();
        log.info("generate node end:{}", LocalDateTime.now());
    }

    /**
     * 月初重置节点
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void resetMemberData() {
        log.info("reset members used data start:{}", LocalDateTime.now());
        memberService.resetMemberData();
        log.info("reset members used data end:{}", LocalDateTime.now());
    }

    /**
     * 刷新Xray 定时任务
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void refreshXray() {
        log.info("refresh xray config  start:{}", LocalDateTime.now());
        serverService.refreshXray();
        log.info("refresh xray config  end:{}", LocalDateTime.now());
    }
}
