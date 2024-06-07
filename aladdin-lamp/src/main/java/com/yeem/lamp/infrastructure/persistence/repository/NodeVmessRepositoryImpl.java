package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yeem.lamp.domain.entity.NodeVmess;
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
    public List<NodeVmess> listByServiceId(Long serviceId) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        queryWrapper.eq(NodeVmessDo::getServiceId, serviceId);
        queryWrapper.eq(NodeVmessDo::getServiceYear, DateUtil.year(new Date()));
        queryWrapper.eq(NodeVmessDo::getServiceMonth, DateUtil.month(new Date()));
        List<NodeVmessDo> nodeVmessDoList = nodeVmessMapper.selectList(queryWrapper);
        return nodeVmessDoList.stream().map(NodeVmessDo::convertNodeVmess).collect(Collectors.toList());
    }

    @Override
    public void expired() {
        LambdaUpdateWrapper<NodeVmessDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.le(NodeVmessDo::getServiceDate, new Date());
        updateWrapper.set(NodeVmessDo::getNodeType, "expired");
        nodeVmessMapper.update(null, updateWrapper);
    }

    @Override
    public int count(Long serverId, Long serviceId, Date currentDate) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        queryWrapper.eq(NodeVmessDo::getServiceId, serviceId);
        queryWrapper.eq(NodeVmessDo::getServerId, serverId);
        queryWrapper.eq(NodeVmessDo::getServiceDate, DateUtil.beginOfDay(currentDate).toJdkDate());
        return nodeVmessMapper.selectCount(queryWrapper);
    }
}
