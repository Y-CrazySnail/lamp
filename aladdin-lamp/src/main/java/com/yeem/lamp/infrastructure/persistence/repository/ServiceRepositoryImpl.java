package com.yeem.lamp.infrastructure.persistence.repository;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.common.entity.BaseEntity;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.repository.ServiceRepository;
import com.yeem.lamp.infrastructure.persistence.entity.NodeVmessDo;
import com.yeem.lamp.infrastructure.persistence.entity.ServiceDo;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.ServiceMapper;
import com.yeem.lamp.security.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public Services getById(Long id) {
        ServiceDo serviceDo = serviceMapper.selectById(id);
        return serviceDo.convertService();
    }

    @Override
    public void save(Services services) {
        ServiceDo serviceDo = new ServiceDo(services);
        serviceMapper.insert(serviceDo);
    }

    @Override
    public void updateById(Services services) {
        ServiceDo serviceDo = new ServiceDo(services);
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
    public List<ServiceDo> listValid() {
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.ge("end_date", new Date());
        queryWrapper.eq("type", ServiceDo.TYPE.SERVICE.getValue());
        List<ServiceDo> serviceDoList = super.list(queryWrapper);
        for (ServiceDo serviceDo : serviceDoList) {
            dealDataTraffic(serviceDo);
        }
        serviceDoList.removeIf(aladdinService -> aladdinService.getSurplus().contains("-"));
        return serviceDoList;
    }

    @Override
    public boolean removeByMemberId(Serializable id) {
        UpdateWrapper<ServiceDo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.TRUE_NUMBER);
        updateWrapper.eq("member_id", id);
        return super.update(updateWrapper);
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
    public ServiceDo getByUUID(String uuid) {
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("uuid", uuid);
        queryWrapper.eq("type", ServiceDo.TYPE.SERVICE.getValue());
        ServiceDo serviceDo = super.getOne(queryWrapper);
        dealDataTraffic(serviceDo);
        return serviceDo;
    }

    @Override
    public void refreshStatus() {
        serviceMapper.refreshStatus();
    }

    @Override
    public void updateUUID(Long memberId, Long serviceId, String uuid) {
        LambdaUpdateWrapper<ServiceDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ServiceDo::getUuid, uuid);
        updateWrapper.eq(ServiceDo::getMemberId, memberId);
        updateWrapper.eq(ServiceDo::getId, serviceId);
        serviceMapper.update(null, updateWrapper);
    }

    private void dealDataTraffic(ServiceDo serviceDo) {
        if (ServiceDo.TYPE.DATA.getValue().equals(serviceDo.getType())) {
            return;
        }
        // 查询数据加量包
        QueryWrapper<ServiceDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(BaseEntity.BaseField.DELETE_FLAG.getName(), Constant.FALSE_NUMBER);
        queryWrapper.eq("member_id", serviceDo.getMemberId());
        queryWrapper.ge("end_date", new Date());
        queryWrapper.eq("type", ServiceDo.TYPE.DATA.getValue());
        List<ServiceDo> serviceDoList = super.list(queryWrapper);
        for (ServiceDo service : serviceDoList) {
            serviceDo.setDataTraffic(serviceDo.getDataTraffic() + service.getDataTraffic());
        }
        int year = DateUtil.year(new Date());
        int month = DateUtil.month(new Date()) + 1;
        List<NodeVmessDo> nodeVmessDoList = aladdinNodeVmessService.listByServiceId(serviceDo.getId(), year, month);
        long up = 0L;
        long down = 0L;
        for (NodeVmessDo nodeVmessDo : nodeVmessDoList) {
            up += nodeVmessDo.getServiceUp() * nodeVmessDo.getMultiplyingPower();
            down += nodeVmessDo.getServiceDown() * nodeVmessDo.getMultiplyingPower();
        }
        Double total = ((double) (up + down) / 1024 / 1024 / 1024);
        String surplus = String.format("%.2f", serviceDo.getDataTraffic() - total);
        serviceDo.setSurplus(surplus);
    }
}
