package com.yeem.lamp.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.security.Constant;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinNodeVmess;
import com.yeem.lamp.infrastructure.persistence.entity.AladdinServer;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.AladdinNodeVmessMapper;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinNodeVmessService;
import com.yeem.lamp.infrastructure.persistence.service.IAladdinServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AladdinNodeVmessServiceImpl extends ServiceImpl<AladdinNodeVmessMapper, AladdinNodeVmess> implements IAladdinNodeVmessService {

    public static final String NODE_TYPE_PRIVATE = "private";
    public static final String NODE_TYPE_PUBLIC = "public";

    @Autowired
    private AladdinNodeVmessMapper aladdinNodeVmessMapper;
    @Autowired
    private IAladdinServerService aladdinServerService;

    @Override
    public List<String> getNodeUrlList(String uuid, String label) {
        List<String> nodeUrlList = new ArrayList<>();
        QueryWrapper<AladdinNodeVmess> privateAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        privateAladdinNodeVmessQueryWrapper.eq("node_id", uuid);
        List<AladdinNodeVmess> privateAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(privateAladdinNodeVmessQueryWrapper);
        for (AladdinNodeVmess aladdinNodeVmess : privateAaladdinNodeVmessList) {
            nodeUrlList.add(aladdinNodeVmess.convert());
        }
        if (!StrUtil.isEmpty(label) && label.contains("whatsapp")) {
            QueryWrapper<AladdinNodeVmess> whatsappAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
            whatsappAladdinNodeVmessQueryWrapper.eq("node_type", "whatsapp");
            List<AladdinNodeVmess> whatsappAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(whatsappAladdinNodeVmessQueryWrapper);
            for (AladdinNodeVmess aladdinNodeVmess : whatsappAaladdinNodeVmessList) {
                nodeUrlList.add(aladdinNodeVmess.convert());
            }
        }
        QueryWrapper<AladdinNodeVmess> publicAladdinNodeVmessQueryWrapper = new QueryWrapper<>();
        publicAladdinNodeVmessQueryWrapper.eq("node_type", "public");
        List<AladdinNodeVmess> publicAaladdinNodeVmessList = aladdinNodeVmessMapper.selectList(publicAladdinNodeVmessQueryWrapper);
        for (AladdinNodeVmess aladdinNodeVmess : publicAaladdinNodeVmessList) {
            nodeUrlList.add(aladdinNodeVmess.convert());
        }
        return nodeUrlList;
    }

    @Override
    public List<AladdinNodeVmess> listByServerId(Long serverId, int year, int month) {
        QueryWrapper<AladdinNodeVmess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("server_id", serverId);
        queryWrapper.eq("service_year", year);
        queryWrapper.eq("service_month", month);
        queryWrapper.eq("node_type", Constant.NODE_TYPE_PRIVATE);
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public List<AladdinNodeVmess> listByServiceId(Long serviceId, int year, int month) {
        QueryWrapper<AladdinNodeVmess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_id", serviceId);
        queryWrapper.eq("service_year", year);
        queryWrapper.eq("service_month", month);
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.orderByAsc("sort");
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public List<AladdinNodeVmess> listByNodeType(String nodeType) {
        QueryWrapper<AladdinNodeVmess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("node_type", nodeType);
        return aladdinNodeVmessMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateByServerId(Long serverId, String nodeType, String nodePs, Integer sort) {
        AladdinServer aladdinServer = aladdinServerService.getById(serverId);
        UpdateWrapper<AladdinNodeVmess> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("multiplying_power", aladdinServer.getMultiplyingPower());
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
    public void save(AladdinServer server, ServiceDo service, int year, int month) {
        LambdaQueryWrapper<AladdinNodeVmess> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AladdinNodeVmess::getDeleteFlag, false);
        queryWrapper.eq(AladdinNodeVmess::getServerId, server.getId());
        queryWrapper.eq(AladdinNodeVmess::getServiceId, service.getId());
        queryWrapper.eq(AladdinNodeVmess::getServiceYear, year);
        queryWrapper.eq(AladdinNodeVmess::getServiceMonth, month);
        queryWrapper.eq(AladdinNodeVmess::getNodeType, Constant.NODE_TYPE_PRIVATE);
        int count = aladdinNodeVmessMapper.selectCount(queryWrapper);
        if (count == 0) {
            AladdinNodeVmess aladdinNodeVmess = new AladdinNodeVmess();
            aladdinNodeVmess.setNodeType(Constant.NODE_TYPE_PRIVATE);
            aladdinNodeVmess.setNodePs(server.getSubscribeNamePrefix());
            aladdinNodeVmess.setNodeAdd(server.getSubscribeIp());
            aladdinNodeVmess.setNodePort(String.valueOf(server.getSubscribePort()));
            aladdinNodeVmess.setNodeId(service.getUuid());
            aladdinNodeVmess.setAid("0");
            aladdinNodeVmess.setNet("tcp");
            aladdinNodeVmess.setType("none");
            aladdinNodeVmess.setTls("none");
            aladdinNodeVmess.setServerId(server.getId());
            aladdinNodeVmess.setServiceId(service.getId());
            aladdinNodeVmess.setServiceYear(year);
            aladdinNodeVmess.setServiceMonth(month);
            aladdinNodeVmess.setServiceUp(0L);
            aladdinNodeVmess.setServiceDown(0L);
            log.info("本地不存在当前节点，创建本地节点：{}", aladdinNodeVmess);
            aladdinNodeVmessMapper.insert(aladdinNodeVmess);
        }
    }
}
