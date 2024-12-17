package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampService;
import com.lamp.entity.LampServiceMonth;
import com.lamp.mapper.LampServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class MLampServiceService extends ServiceImpl<LampServiceMapper, LampService> {

    @Autowired
    private LampServiceMapper serviceMapper;

    @Autowired
    private MLampServiceMonthService serviceMonthService;

    @Override
    public boolean save(LampService entity) {
        super.save(entity);
        LampServiceMonth serviceMonth = LampServiceMonth.generate(entity);
        serviceMonthService.save(serviceMonth);
        return true;
    }

    @Override
    public boolean updateBatchById(Collection<LampService> entityList) {
        super.updateBatchById(entityList);
        for (LampService service : entityList) {
            if (Objects.isNull(service.getServiceMonthList()) || service.getServiceMonthList().isEmpty()) {
                continue;
            }
            serviceMonthService.updateBatchById(service.getServiceMonthList());
        }
        return true;
    }

    public void setServiceList(LampMember member) {
        LambdaQueryWrapper<LampService> queryWrapper = new LambdaQueryWrapper<>(LampService.class);
        queryWrapper.eq(LampService::getMemberId, member.getId());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampService> serviceList = serviceMapper.selectList(queryWrapper);
        for (LampService service : serviceList) {
            serviceMonthService.setServiceMonthList(service);
        }
        member.setServiceList(serviceList);
    }

}
