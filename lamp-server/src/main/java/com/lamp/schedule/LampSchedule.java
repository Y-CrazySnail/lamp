package com.lamp.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lamp.entity.LampInbound;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampServer;
import com.lamp.entity.LampService;
import com.lamp.service.manage.*;
import com.lamp.infrastructure.xui.XServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LampSchedule {

    @Autowired
    private MLampMemberService memberService;

    @Autowired
    private MLampServiceService serviceService;

    @Autowired
    private MLampInboundService inboundService;

    @Autowired
    private MLampServerService serverService;

    @Autowired
    private MLampClientTrafficService clientTrafficService;

    /**
     * 每小时执行
     */
    @Scheduled(cron = "0 40 0/1 * * ?")
    public void sync() {
        try {
            serverService.sync(null, null);
            memberService.syncBandwidth(null);
        } catch (Exception e) {
            log.error("sync error", e);
        }
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void reset() {
        // 重置远程流量
        List<LampServer> serverList = serverService.list();
        for (LampServer server : serverList) {
            inboundService.setInboundList(server);
            XServer xServer = XServer.init(server.getApiIp(), server.getApiPort(), server.getApiUsername(), server.getApiPassword());
            if (!server.getInboundList().isEmpty()) {
                for (LampInbound inbound : server.getInboundList()) {
                    xServer.resetClientTraffic(inbound.getXuiId());
                }
            }
        }
        // 删除本地流量
        clientTrafficService.remove(new QueryWrapper<>());
        // 备份会员老月份流量使用情况
        List<LampMember> memberList = memberService.list().stream().filter(LampMember::isValid).collect(Collectors.toList());
        List<LampService> serviceList = new ArrayList<>();
        for (LampMember member : memberList) {
            LampService service = LampService.generate(member);
            serviceList.add(service);
            member.resetBandwidth();
        }
        serviceService.saveBatch(serviceList);
        // 重置会员新月份流量
        memberService.updateBatchById(memberList);
    }
}
