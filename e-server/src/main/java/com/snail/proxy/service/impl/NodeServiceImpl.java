package com.snail.proxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.proxy.entity.Node;
import com.snail.proxy.entity.Server;
import com.snail.proxy.mapper.NodeMapper;
import com.snail.proxy.service.INodeService;
import com.snail.proxy.service.IServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements INodeService {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private IServerService serverService;

    public void generateNode() {
        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.le("member_id", 0);
        List<Node> nodeList = nodeMapper.selectList(nodeQueryWrapper);
        int length = nodeList.size();
        long memberId = -System.currentTimeMillis();
        while (length < 10) {
            Node node = new Node();
            List<Server> serverList = serverService.list(new QueryWrapper<>());
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
