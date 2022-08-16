package com.snail.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snail.entity.*;
import com.snail.mapper.MemberMapper;
import com.snail.mapper.NodeMapper;
import com.snail.mapper.ServerMapper;
import com.snail.mapper.TrafficMapper;
import com.snail.service.ICommandRecordService;
import com.snail.service.INodeService;
import com.snail.service.IServerService;
import com.snail.service.ITrafficService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProxySchedule {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ServerMapper serverMapper;

    @Autowired
    private TrafficMapper trafficMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IServerService serverService;

    @Scheduled(cron = "0 5 1 * * ?")
    public void schedule() {
        generateNode();
        calculateTraffic();
    }

    @Scheduled(cron = "0 0 2 1 * ?")
    public void resetTraffic() {
        memberMapper.resetTraffic();
    }

    /**
     * 计算流量
     */
    private void calculateTraffic() {
        QueryWrapper<Traffic> trafficQueryWrapper = new QueryWrapper<>();
        trafficQueryWrapper.eq("flag", 0);
        List<Traffic> trafficList = trafficMapper.selectList(trafficQueryWrapper);
        trafficList.forEach(traffic -> {
            Member member = memberMapper.selectById(traffic.getMemberId());
            if (member != null) {
                if (traffic.getType().equals("downlink")) {
                    member.setTrafficDownMonth(member.getTrafficDownMonth() + traffic.getTraffic());
                }
                if (traffic.getType().equals("uplink")) {
                    member.setTrafficUpMonth(member.getTrafficUpMonth() + traffic.getTraffic());
                }
                member.setTrafficSurplusMonth(member.getTrafficSurplusMonth() - traffic.getTraffic());
                memberMapper.updateById(member);
            }
            traffic.setFlag(1);
            trafficMapper.updateById(traffic);
        });
    }

    /**
     * 生成新节点
     */
    private void generateNode() {
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.le("member_id", 0);
        List<Node> nodeList = nodeMapper.selectList(nodeQueryWrapper);
        int length = nodeList.size();
        long memberId = -System.currentTimeMillis();
        while (length < 10) {
            Node node = new Node();
            List<Server> serverList = serverMapper.selectList(new QueryWrapper<>());
            node.setMemberId(memberId - length);
            node.setServerId(serverList.get(length % serverList.size()).getId());
            node.setUuid(UUID.randomUUID().toString());
            node.setDomain(serverList.get(length % serverList.size()).getIp());
            node.setPort(443);
            node.setType("ws");
            node.setSecurity("tls");
            node.setPath("%2Fc5fa7e2466516a1%2F");
            node.setCreateUser("system");
            node.setCreateTime(LocalDateTime.now());
            node.setUpdateUser("system");
            node.setUpdateTime(LocalDateTime.now());
            nodeMapper.insert(node);
            length++;
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void refreshXray() {
        serverService.refreshXray();
    }
}
