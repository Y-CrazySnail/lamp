package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.entity.Server;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.repository.ServiceRepository;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceMonthDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private NodeVmessMapper nodeVmessMapper;
    @Autowired
    private ServiceMonthMapper serviceMonthMapper;
    @Autowired
    private ServiceRecordMapper serviceRecordMapper;
    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Override
    public Services getByUUID(String uuid) {
        LambdaQueryWrapper<ServiceDo> serviceDoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        serviceDoLambdaQueryWrapper.eq(ServiceDo::getDeleteFlag, false);
        serviceDoLambdaQueryWrapper.eq(ServiceDo::getUuid, uuid);
        ServiceDo serviceDo = serviceMapper.selectOne(serviceDoLambdaQueryWrapper);
        Services services = serviceDo.convertService();

        LambdaQueryWrapper<ServerDo> serverDoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        serverDoLambdaQueryWrapper.eq(ServerDo::getDeleteFlag, false);
        List<ServerDo> serverDoList = serverMapper.selectList(serverDoLambdaQueryWrapper);
        List<Server> serverList = serverDoList.stream().map(ServerDo::convertServer).collect(Collectors.toList());
        services.setServerList(serverList);

        return services;
    }

    @Override
    public List<Services> listService(Services services) {
        LambdaQueryWrapper<ServiceDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceDo::getDeleteFlag, false);
        if (StrUtil.isNotEmpty(services.getUuid())) {
            queryWrapper.eq(ServiceDo::getUuid, services.getUuid());
        }
        List<ServiceDo> serviceDoList = serviceMapper.selectList(queryWrapper);
        return serviceDoList.stream().map(ServiceDo::convertService).collect(Collectors.toList());
    }

    @Override
    public Services getServiceById(Long serviceId) {
        ServiceDo serviceDo = serviceMapper.selectById(serviceId);
        return serviceDo.convertService();
    }

    @Override
    public Server getServerById(Long serverId) {
        ServerDo serverDo = serverMapper.selectById(serverId);
        return serverDo.convertServer();
    }

    @Override
    public void save(Services services) {
        ServiceDo serviceDo = ServiceDo.init(services);
        serviceMapper.insert(serviceDo);
    }

    @Override
    public void updateById(Services services) {
        ServiceDo serviceDo = ServiceDo.init(services);
        serviceMapper.updateById(serviceDo);
    }

    @Override
    public void removeById(Long id) {
        LambdaUpdateWrapper<ServiceDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ServiceDo::getDeleteFlag, true);
        updateWrapper.eq(ServiceDo::getId, id);
        serviceMapper.update(null, updateWrapper);
    }

    /**
     * 根据会员ID查询服务列表
     *
     * @param memberId 会员ID
     * @return 服务列表
     */
    @Override
    public List<Services> listByMemberId(Long memberId) {
        LambdaQueryWrapper<ServiceDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceDo::getMemberId, memberId);
        queryWrapper.eq(ServiceDo::getDeleteFlag, false);
        List<ServiceDo> serviceDoList = serviceMapper.selectList(queryWrapper);
        return serviceDoList.stream().map(ServiceDo::convertService).collect(Collectors.toList());
    }


    @Override
    public IPage<Services> pages(int current, int size, Long memberId, String status, String wechat, String email) {
        IPage<ServiceDo> page = new Page<>(current, size);
        page = serviceMapper.selectPages(page, memberId, status, wechat, email);
        IPage<Services> res = new Page<>();
        res.setPages(page.getPages());
        res.setCurrent(page.getCurrent());
        res.setRecords(page.getRecords().stream().map(ServiceDo::convertService).collect(Collectors.toList()));
        res.setSize(page.getSize());
        res.setTotal(page.getTotal());
        return res;
    }

    @Override
    public List<Server> listServer() {
        LambdaQueryWrapper<ServerDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServerDo::getDeleteFlag, false);
        List<ServerDo> serverDoList = serverMapper.selectList(queryWrapper);
        return serverDoList.stream().map(ServerDo::convertServer).collect(Collectors.toList());
    }

    @Override
    public List<NodeVmess> listNodeVmess(Date currentDate) {
        LambdaQueryWrapper<NodeVmessDo> queryWrapper = new LambdaQueryWrapper<>();
        if (null != currentDate) {
            queryWrapper.eq(NodeVmessDo::getServiceDate, DateUtil.beginOfDay(currentDate).toJdkDate());
        }
        List<NodeVmessDo> nodeVmessDoList = nodeVmessMapper.selectList(queryWrapper);
        return nodeVmessDoList.stream().map(NodeVmessDo::convertNodeVmess).collect(Collectors.toList());
    }

    @Override
    public void saveNodeVmess(NodeVmess nodeVmess) {
        NodeVmessDo nodeVmessDo = NodeVmessDo.init(nodeVmess);
        if (null == nodeVmessDo.getId()) {
            nodeVmessMapper.insert(nodeVmessDo);
        } else {
            nodeVmessMapper.updateById(nodeVmessDo);
        }
    }

    @Override
    public void updateService(Services services) {
        ServiceDo serviceDo = ServiceDo.init(services);
        serviceMapper.updateById(serviceDo);
    }

    @Override
    public List<ServiceMonth> listServiceMonth(Integer year, Integer month) {
        LambdaQueryWrapper<ServiceMonthDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceMonthDo::getDeleteFlag, false);
        queryWrapper.eq(ServiceMonthDo::getServiceYear, year);
        queryWrapper.eq(ServiceMonthDo::getServiceMonth, month);
        List<ServiceMonthDo> serviceMonthDoList = serviceMonthMapper.selectList(queryWrapper);
        return serviceMonthDoList.stream().map(ServiceMonthDo::convertServiceMonth).collect(Collectors.toList());
    }

    @Override
    public void batchSaveServiceMonth(List<ServiceMonth> serviceMonthList) {
        List<ServiceMonthDo> serviceMonthDoList = serviceMonthList.stream().map(ServiceMonthDo::init).collect(Collectors.toList());
        for (ServiceMonthDo serviceMonthDo : serviceMonthDoList) {
            serviceMonthMapper.insert(serviceMonthDo);
        }
    }
}
