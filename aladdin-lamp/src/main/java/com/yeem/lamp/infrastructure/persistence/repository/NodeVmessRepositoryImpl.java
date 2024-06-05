package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.repository.NodeVmessRepository;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.security.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NodeVmessRepositoryImpl implements NodeVmessRepository {
    @Override
    public List<String> getNodeUrlList(String uuid, String label) {
        List<String> nodeUrlList = new ArrayList<>();
        QueryWrapper<NodeVmessDo> privateAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        privateAladdinNodeVmessQueryWrapper.eq("node_id", uuid);
        List<NodeVmessDo> privateAaladdinNodeVmessDoList = aladdinNodeVmessMapper.selectList(privateAladdinNodeVmessQueryWrapper);
        for (NodeVmessDo nodeVmessDo : privateAaladdinNodeVmessDoList) {
            nodeUrlList.add(nodeVmessDo.convert());
        }
        if (!StrUtil.isEmpty(label) && label.contains("whatsapp")) {
            QueryWrapper<NodeVmessDo> whatsappAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
            whatsappAladdinNodeVmessQueryWrapper.eq("node_type", "whatsapp");
            List<NodeVmessDo> whatsappAaladdinNodeVmessDoList = aladdinNodeVmessMapper.selectList(whatsappAladdinNodeVmessQueryWrapper);
            for (NodeVmessDo nodeVmessDo : whatsappAaladdinNodeVmessDoList) {
                nodeUrlList.add(nodeVmessDo.convert());
            }
        }
        QueryWrapper<NodeVmessDo> publicAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        publicAladdinNodeVmessQueryWrapper.eq("node_type", "public");
        List<NodeVmessDo> publicAaladdinNodeVmessDoList = aladdinNodeVmessMapper.selectList(publicAladdinNodeVmessQueryWrapper);
        for (NodeVmessDo nodeVmessDo : publicAaladdinNodeVmessDoList) {
            nodeUrlList.add(nodeVmessDo.convert());
        }
        return nodeUrlList;
    }

    @Override
    public List<NodeVmessDo> listByServerId(Long serverId, int year, int month) {
        QueryWrapper<NodeVmessDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("server_id", serverId);
        queryWrapper.eq("service_year", year);
        queryWrapper.eq("service_month", month);
        queryWrapper.eq("node_type", Constant.NODE_TYPE_PRIVATE);
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public List<NodeVmessDo> listByServiceId(Long serviceId, int year, int month) {
        QueryWrapper<NodeVmessDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_id", serviceId);
        queryWrapper.eq("service_year", year);
        queryWrapper.eq("service_month", month);
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.orderByAsc("sort");
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public List<NodeVmessDo> listByNodeType(String nodeType) {
        QueryWrapper<NodeVmessDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("node_type", nodeType);
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateByServerId(Long serverId, String nodeType, String nodePs, Integer sort) {
        ServerDo serverDo = aladdinServerService.getById(serverId);
        UpdateWrapper<NodeVmessDo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("multiplying_power", serverDo.getMultiplyingPower());
        if (!StrUtil.isEmpty(nodePs)) {
            updateWrapper.set("node_ps", nodePs);
        }
        if (!StrUtil.isEmpty(nodeType)) {
            updateWrapper.set("node_type", nodeType);
        }
        if (null != sort) {
            updateWrapper.set("sort", sort);
        }
        updateWrapper.eq("server_id", serverId);
        return super.update(updateWrapper);
    }

    @Override
    public void updateByValidServiceList(List<ServiceDo> serviceList) {
        aladdinNodeVmessMapper.updateByValidServiceIdList(serviceList.stream().map(ServiceDo::getId).collect(Collectors.toList()));
    }

    @Override
    public void save(ServerDo server, ServiceDo service, int year, int month) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeVmessDo::getDeleteFlag, false);
        queryWrapper.eq(NodeVmessDo::getServerId, server.getId());
        queryWrapper.eq(NodeVmessDo::getServiceId, service.getId());
        queryWrapper.eq(NodeVmessDo::getServiceYear, year);
        queryWrapper.eq(NodeVmessDo::getServiceMonth, month);
        queryWrapper.eq(NodeVmessDo::getNodeType, Constant.NODE_TYPE_PRIVATE);
        int count = aladdinNodeVmessMapper.selectCount(queryWrapper);
        if (count == 0) {
            NodeVmessDo nodeVmessDo = new NodeVmessDo();
            nodeVmessDo.setNodeType(Constant.NODE_TYPE_PRIVATE);
            nodeVmessDo.setNodePs(server.getSubscribeNamePrefix());
            nodeVmessDo.setNodeAdd(server.getSubscribeIp());
            nodeVmessDo.setNodePort(String.valueOf(server.getSubscribePort()));
            nodeVmessDo.setNodeId(service.getUuid());
            nodeVmessDo.setAid("0");
            nodeVmessDo.setNet("tcp");
            nodeVmessDo.setType("none");
            nodeVmessDo.setTls("none");
            nodeVmessDo.setServerId(server.getId());
            nodeVmessDo.setServiceId(service.getId());
            nodeVmessDo.setServiceYear(year);
            nodeVmessDo.setServiceMonth(month);
            nodeVmessDo.setServiceUp(0L);
            nodeVmessDo.setServiceDown(0L);
            log.info("本地不存在当前节点，创建本地节点：{}", nodeVmessDo);
            aladdinNodeVmessMapper.insert(nodeVmessDo);
        }
    }
}
