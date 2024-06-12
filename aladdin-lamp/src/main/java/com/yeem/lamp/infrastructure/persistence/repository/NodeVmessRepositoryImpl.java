package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.repository.NodeVmessRepository;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.NodeVmessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NodeVmessRepositoryImpl implements NodeVmessRepository {

    @Autowired
    private NodeVmessMapper nodeVmessMapper;

    @Override
    public List<NodeVmess> list(String nodeType, Long serverId, Date currentDate) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getServerId, serverId);
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        if (StrUtil.isNotEmpty(nodeType)) {
            queryWrapper.eq(NodeVmessDo::getNodeType, nodeType);
        }
        queryWrapper.eq(NodeVmessDo::getServiceYear, DateUtil.year(new Date()));
        queryWrapper.eq(NodeVmessDo::getServiceMonth, DateUtil.month(new Date()) + 1);
        queryWrapper.eq(NodeVmessDo::getServiceDate, DateUtil.beginOfDay(currentDate).toJdkDate());
        List<NodeVmessDo> nodeVmessDoList = nodeVmessMapper.selectList(queryWrapper);
        return nodeVmessDoList.stream().map(NodeVmessDo::convertNodeVmess).collect(Collectors.toList());
    }

    @Override
    public List<NodeVmess> list(Long serverId, Long serviceId, Date currentDate) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        queryWrapper.eq(NodeVmessDo::getServiceId, serviceId);
        queryWrapper.eq(NodeVmessDo::getServiceYear, DateUtil.year(new Date()));
        queryWrapper.eq(NodeVmessDo::getServiceMonth, DateUtil.month(new Date()) + 1);
        queryWrapper.eq(NodeVmessDo::getServiceDate, DateUtil.beginOfDay(currentDate).toJdkDate());
        List<NodeVmessDo> nodeVmessDoList = nodeVmessMapper.selectList(queryWrapper);
        return nodeVmessDoList.stream().map(NodeVmessDo::convertNodeVmess).collect(Collectors.toList());
    }

    @Override
    public List<NodeVmess> listByServiceId(Long serviceId) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        queryWrapper.eq(NodeVmessDo::getServiceId, serviceId);
        queryWrapper.eq(NodeVmessDo::getServiceYear, DateUtil.year(new Date()));
        queryWrapper.eq(NodeVmessDo::getServiceMonth, DateUtil.month(new Date()) + 1);
        List<NodeVmessDo> nodeVmessDoList = nodeVmessMapper.selectList(queryWrapper);
        return nodeVmessDoList.stream().map(NodeVmessDo::convertNodeVmess).collect(Collectors.toList());
    }

    @Override
    public List<NodeVmess> list(Long serviceId, Date currentDate) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        queryWrapper.eq(NodeVmessDo::getServiceId, serviceId);
        queryWrapper.eq(NodeVmessDo::getServiceYear, DateUtil.year(currentDate));
        queryWrapper.eq(NodeVmessDo::getServiceMonth, DateUtil.month(currentDate) + 1);
        queryWrapper.eq(NodeVmessDo::getServiceDate, currentDate);
        List<NodeVmessDo> nodeVmessDoList = nodeVmessMapper.selectList(queryWrapper);
        return nodeVmessDoList.stream().map(NodeVmessDo::convertNodeVmess).collect(Collectors.toList());
    }

    @Override
    public void expired() {
        LambdaUpdateWrapper<NodeVmessDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.lt(NodeVmessDo::getServiceDate, DateUtil.beginOfDay(new Date()).toJdkDate());
        updateWrapper.set(NodeVmessDo::getNodeType, "expired");
        nodeVmessMapper.update(null, updateWrapper);
    }

    @Override
    public int count(Long serverId, Long serviceId, String uuid, Date currentDate) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        queryWrapper.eq(NodeVmessDo::getServiceId, serviceId);
        queryWrapper.eq(NodeVmessDo::getServerId, serverId);
        queryWrapper.eq(NodeVmessDo::getNodeId, uuid);
        queryWrapper.eq(NodeVmessDo::getServiceDate, DateUtil.beginOfDay(currentDate).toJdkDate());
        return nodeVmessMapper.selectCount(queryWrapper);
    }

    @Override
    public void insert(NodeVmess nodeVmess) {
        NodeVmessDo nodeVmessDo = NodeVmessDo.init(nodeVmess);
        nodeVmessMapper.insert(nodeVmessDo);
    }

    @Override
    public void updateById(NodeVmess nodeVmess) {
        NodeVmessDo nodeVmessDo = NodeVmessDo.init(nodeVmess);
        nodeVmessMapper.updateById(nodeVmessDo);
    }
}
