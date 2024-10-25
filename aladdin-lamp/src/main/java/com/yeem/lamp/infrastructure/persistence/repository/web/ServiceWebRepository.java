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

    /**
     * 查询服务列表
     *
     * @return 服务列表
     */
    public List<Services> list() {
        LambdaQueryWrapper<ServiceDo> queryWrapper = Wrappers.lambdaQuery(ServiceDo.class);
        List<ServiceDo> serviceDoList = serviceMapper.selectList(queryWrapper);
        return serviceDoList.stream().map(ServiceDo::convertService).collect(Collectors.toList());
    }


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
        List<ServiceMonthDo> serviceMonthDoList = serviceMonthMapper.selectList(queryWrapper);
        return serviceMonthDoList.stream().map(ServiceMonthDo::convertServiceMonth).collect(Collectors.toList());
    }

    public List<ServiceRecord> listServiceRecord(ServiceRecord serviceRecord, Date current) {
        LambdaQueryWrapper<ServiceRecordDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ServiceRecordDo::getDeleteFlag, false);
        queryWrapper.eq(ServiceRecordDo::getServiceId, serviceRecord.getServiceId());
        queryWrapper.eq(ServiceRecordDo::getServiceMonthId, serviceRecord.getServiceMonthId());
        if (null != current) {
            Date begin = DateUtil.beginOfDay(DateUtil.beginOfMonth(current)).toJdkDate();
            Date end = DateUtil.beginOfDay(DateUtil.endOfMonth(current)).toJdkDate();
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

    public void updateById(Services services) {
        ServiceDo serviceDo = ServiceDo.init(services);
        serviceMapper.updateById(serviceDo);
    }

    public void removeById(Long id) {
        LambdaUpdateWrapper<ServiceDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ServiceDo::getDeleteFlag, true);
        updateWrapper.eq(ServiceDo::getId, id);
        serviceMapper.update(null, updateWrapper);
    }

    public void removeByMemberId(Long memberId) {
        LambdaUpdateWrapper<ServiceDo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ServiceDo::getDeleteFlag, true);
        updateWrapper.eq(ServiceDo::getMemberId, memberId);
        serviceMapper.update(null, updateWrapper);
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

}
