package com.lamp.service.manage;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampService;
import com.lamp.entity.LampServiceMonth;
import com.lamp.mapper.LampServiceMonthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MLampServiceMonthService extends ServiceImpl<LampServiceMonthMapper, LampServiceMonth> {

    @Autowired
    private LampServiceMonthMapper serviceMonthMapper;

    @Autowired
    private MLampServiceService serviceService;

    @Autowired
    private MLampClientTrafficService clientTrafficService;

    public List<LampServiceMonth> list(Date current) {
        int year = DateUtil.year(current);
        int month = DateUtil.month(current) + 1;
        LambdaQueryWrapper<LampServiceMonth> queryWrapper = new LambdaQueryWrapper<>(LampServiceMonth.class);
        queryWrapper.eq(LampServiceMonth::getServiceYear, year);
        queryWrapper.eq(LampServiceMonth::getServiceMonth, month);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return serviceMonthMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<LampServiceMonth> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean removeById(Serializable id) {
        LambdaUpdateWrapper<LampServiceMonth> updateWrapper = new LambdaUpdateWrapper<>(LampServiceMonth.class);
        updateWrapper.set(LampServiceMonth::getDeleteFlag, true);
        updateWrapper.eq(LampServiceMonth::getId, id);
        serviceMonthMapper.update(null, updateWrapper);
        return true;
    }

    public void removeByServiceId(Long serviceId) {
        LambdaUpdateWrapper<LampServiceMonth> updateWrapper = new LambdaUpdateWrapper<>(LampServiceMonth.class);
        updateWrapper.set(LampServiceMonth::getDeleteFlag, true);
        updateWrapper.eq(LampServiceMonth::getServiceId, serviceId);
        serviceMonthMapper.update(null, updateWrapper);
    }

    public void setServiceMonthList(LampService service, Date current) {
        LambdaQueryWrapper<LampServiceMonth> queryWrapper = new LambdaQueryWrapper<>(LampServiceMonth.class);
        queryWrapper.eq(LampServiceMonth::getServiceId, service.getId());
        if (Objects.nonNull(current)) {
            int year = DateUtil.year(current);
            int month = DateUtil.month(current) + 1;
            queryWrapper.eq(LampServiceMonth::getServiceYear, year);
            queryWrapper.eq(LampServiceMonth::getServiceMonth, month);
        }
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampServiceMonth> serviceMonthList = serviceMonthMapper.selectList(queryWrapper);
        for (LampServiceMonth serviceMonth : serviceMonthList) {
            clientTrafficService.setClientTrafficList(serviceMonth);
        }
        service.setServiceMonthList(serviceMonthList);
    }

    /**
     * 同步月服务
     */
    public void sync(Date current) {
        int year = DateUtil.year(current);
        int month = DateUtil.month(current) + 1;
        List<LampService> serviceList = serviceService.list().stream().filter(LampService::isNotExpired).collect(Collectors.toList());
        Set<Long> serviceIdSet = serviceList.stream().map(LampService::getId).collect(Collectors.toSet());

        // 做月服务增量同步
        for (LampService service : serviceList) {
            LambdaQueryWrapper<LampServiceMonth> queryWrapper = new LambdaQueryWrapper<>(LampServiceMonth.class);
            BaseEntity.setDeleteFlagCondition(queryWrapper);
            queryWrapper.eq(LampServiceMonth::getServiceYear, year);
            queryWrapper.eq(LampServiceMonth::getServiceMonth, month);
            queryWrapper.eq(LampServiceMonth::getServiceId, service.getId());
            List<LampServiceMonth> serviceMonthList = serviceMonthMapper.selectList(queryWrapper);
            if (serviceMonthList.size() > 1) {
                // todo 发异常提醒
            }
            if (serviceMonthList.isEmpty()) {
                LampServiceMonth serviceMonth = LampServiceMonth.generate(service);
                serviceMonthMapper.insert(serviceMonth);
            } else {
                for (LampServiceMonth serviceMonth : serviceMonthList) {
                    serviceMonth.setUuid(service.getUuid());
                    serviceMonth.setExpiryDate(service.getExpiryDate());
                    serviceMonthMapper.updateById(serviceMonth);
                }
            }
        }

        // 做月服务减量同步
        List<LampServiceMonth> serviceMonthList = list(current);
        for (LampServiceMonth serviceMonth : serviceMonthList) {
            if (!serviceIdSet.contains(serviceMonth.getServiceId())) {
                removeById(serviceMonth.getId());
            }
        }
    }
}
