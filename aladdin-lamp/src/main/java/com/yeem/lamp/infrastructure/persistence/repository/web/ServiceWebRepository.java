package com.yeem.lamp.infrastructure.persistence.repository.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeem.lamp.domain.entity.Services;
import com.yeem.lamp.domain.objvalue.ServiceMonth;
import com.yeem.lamp.domain.objvalue.ServiceRecord;
import com.yeem.lamp.domain.objvalue.Subscription;
import com.yeem.lamp.infrastructure.persistence.entity.*;
import com.yeem.lamp.infrastructure.persistence.repository.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ServiceWebRepository {

    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private ServiceMonthMapper serviceMonthMapper;
    @Autowired
    private ServiceRecordMapper serviceRecordMapper;
    @Autowired
    private SubscriptionMapper subscriptionMapper;

    public Services getByUUID(String uuid) {
        LambdaQueryWrapper<ServiceDo> serviceDoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        serviceDoLambdaQueryWrapper.eq(ServiceDo::getDeleteFlag, false);
        serviceDoLambdaQueryWrapper.eq(ServiceDo::getUuid, uuid);
        ServiceDo serviceDo = serviceMapper.selectOne(serviceDoLambdaQueryWrapper);
        return serviceDo.convertService();
    }

    public List<Services> listService() {
        LambdaQueryWrapper<ServiceDo> queryWrapper = new LambdaQueryWrapper<>();
        List<ServiceDo> serviceDoList = serviceMapper.selectList(queryWrapper);
        return serviceDoList.stream().map(ServiceDo::convertService).collect(Collectors.toList());
    }

    public List<ServiceMonth> listServiceMonth(ServiceMonth serviceMonth) {
        LambdaQueryWrapper<ServiceMonthDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceMonthDo::getDeleteFlag, false);
        if (null != serviceMonth.getServiceId()) {
            queryWrapper.eq(ServiceMonthDo::getServiceId, serviceMonth.getServiceId());
        }
        if (null != serviceMonth.getServiceYear()) {
            queryWrapper.eq(ServiceMonthDo::getServiceYear, serviceMonth.getServiceYear());
        }
        if (null != serviceMonth.getServiceMonth()) {
            queryWrapper.eq(ServiceMonthDo::getServiceMonth, serviceMonth.getServiceMonth());
        }
        queryWrapper.orderByDesc(ServiceMonthDo::getId);
        List<ServiceMonthDo> serviceMonthDoList = serviceMonthMapper.selectList(queryWrapper);
        return serviceMonthDoList.stream().map(ServiceMonthDo::convertServiceMonth).collect(Collectors.toList());
    }

    public List<ServiceRecord> listServiceRecord(ServiceRecord serviceRecord, Date date) {
        LambdaQueryWrapper<ServiceRecordDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceRecordDo::getServiceId, serviceRecord.getServiceId());
        queryWrapper.eq(ServiceRecordDo::getServiceMonthId, serviceRecord.getServiceMonthId());
        if (null != date) {
            Date begin = DateUtil.beginOfDay(DateUtil.beginOfMonth(date)).toJdkDate();
            Date end = DateUtil.endOfDay(DateUtil.endOfMonth(date)).toJdkDate();
            queryWrapper.ge(ServiceRecordDo::getServiceDate, begin);
            queryWrapper.le(ServiceRecordDo::getServiceDate, end);
        }
        List<ServiceRecordDo> serviceRecordDoList = serviceRecordMapper.selectList(queryWrapper);
        return serviceRecordDoList.stream().map(ServiceRecordDo::convertServiceRecord).collect(Collectors.toList());
    }

    public List<Subscription> listSubscription() {
        LambdaQueryWrapper<SubscriptionDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubscriptionDo::getDeleteFlag, false);
        queryWrapper.orderByAsc(SubscriptionDo::getSort);
        List<SubscriptionDo> subscriptionDoList = subscriptionMapper.selectList(queryWrapper);
        return subscriptionDoList.stream().map(SubscriptionDo::convertSubscription).collect(Collectors.toList());
    }

    public Services getServiceById(Long serviceId) {
        ServiceDo serviceDo = serviceMapper.selectById(serviceId);
        return serviceDo.convertService();
    }

    public void save(Services services) {
        ServiceDo serviceDo = ServiceDo.init(services);
        if (null == services.getId()) {
            serviceMapper.insert(serviceDo);
            services.setId(serviceDo.getId());
        } else {
            serviceMapper.updateById(serviceDo);
        }
        if (services.getCurrentServiceMonth() == null) {
            return;
        }
        ServiceMonth serviceMonth = services.getCurrentServiceMonth();
        ServiceMonthDo serviceMonthDo = ServiceMonthDo.init(serviceMonth);
        if (null == serviceMonth.getId()) {
            serviceMonthMapper.insert(serviceMonthDo);
        } else {
            serviceMonthMapper.updateById(serviceMonthDo);
        }
        List<ServiceRecord> serviceRecordList = serviceMonth.getServiceRecordList();
        if (null == serviceRecordList) {
            return;
        }
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        for (ServiceRecord record : serviceRecordList) {
            if (!current.equals(record.getServiceDate())) {
                continue;
            }
            ServiceRecordDo serviceRecordDo = ServiceRecordDo.init(record);
            if (null == record.getId()) {
                serviceRecordMapper.insert(serviceRecordDo);
            } else {
                serviceRecordMapper.updateById(serviceRecordDo);
            }
        }
    }

    public void saveService(Services services) {
        ServiceDo serviceDo = ServiceDo.init(services);
        if (null == services.getId()) {
            serviceMapper.insert(serviceDo);
            services.setId(serviceDo.getId());
        } else {
            serviceMapper.updateById(serviceDo);
        }
    }

    public void saveServiceMonth(ServiceMonth serviceMonth) {
        serviceMonth.syncBandwidth();
        ServiceMonthDo serviceMonthDo = ServiceMonthDo.init(serviceMonth);
        if (null == serviceMonth.getId()) {
            serviceMonthMapper.insert(serviceMonthDo);
        } else {
            serviceMonthMapper.updateById(serviceMonthDo);
        }
        List<ServiceRecord> serviceRecordList = serviceMonth.getServiceRecordList();
        if (null == serviceRecordList) {
            return;
        }
        Date current = DateUtil.beginOfDay(new Date()).toJdkDate();
        for (ServiceRecord record : serviceRecordList) {
            if (!current.equals(record.getServiceDate())) {
                continue;
            }
            ServiceRecordDo serviceRecordDo = ServiceRecordDo.init(record);
            if (null == record.getId()) {
                serviceRecordMapper.insert(serviceRecordDo);
            } else {
                serviceRecordMapper.updateById(serviceRecordDo);
            }
        }
    }

    /**
     * 根据会员ID查询服务列表
     *
     * @param memberId 会员ID
     * @return 服务列表
     */
    public List<Services> listByMemberId(Long memberId) {
        LambdaQueryWrapper<ServiceDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceDo::getMemberId, memberId);
        queryWrapper.eq(ServiceDo::getDeleteFlag, false);
        List<ServiceDo> serviceDoList = serviceMapper.selectList(queryWrapper);
        return serviceDoList.stream().map(ServiceDo::convertService).collect(Collectors.toList());
    }

}
