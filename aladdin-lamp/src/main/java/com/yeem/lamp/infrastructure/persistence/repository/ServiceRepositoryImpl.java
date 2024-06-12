package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.NodeVmess;
import com.yeem.lamp.domain.objvalue.Server;
import com.yeem.lamp.domain.repository.ServiceRepository;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServerDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.NodeVmessMapper;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.ServerMapper;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.ServiceMapper;
import com.yeem.lamp.security.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    @Override
    public Services getById(Long id) {
        ServiceDo serviceDo = serviceMapper.selectById(id);
        return serviceDo.convertService();
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
    public List<Services> listService() {
        LambdaQueryWrapper<ServiceDo> queryWrapper = new LambdaQueryWrapper<>();
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
    public Services getByUUID(String uuid) {
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        queryWrapper.eq("type", ServiceDo.TYPE.SERVICE.getValue());
        ServiceDo serviceDo = serviceMapper.selectOne(queryWrapper);
        return serviceDo.convertService();
    }

    @Override
    public void updateUUID(Long memberId, Long serviceId, String uuid) {
        LambdaUpdateWrapper<ServiceDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ServiceDo::getUuid, uuid);
        updateWrapper.eq(ServiceDo::getMemberId, memberId);
        updateWrapper.eq(ServiceDo::getId, serviceId);
        serviceMapper.update(null, updateWrapper);
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
}
