package com.snail.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snail.entity.Member;
import com.snail.entity.Node;
import com.snail.entity.Server;
import com.snail.entity.Traffic;
import com.snail.mapper.MemberMapper;
import com.snail.mapper.NodeMapper;
import com.snail.mapper.ServerMapper;
import com.snail.mapper.TrafficMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Scheduled(cron = "0 5 1 * * ?")
    public void schedule() {
        generateNode();
        calculateTraffic();
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
}
