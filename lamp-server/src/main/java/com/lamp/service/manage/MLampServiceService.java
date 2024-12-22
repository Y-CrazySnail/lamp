package com.lamp.service.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampMember;
import com.lamp.entity.LampService;
import com.lamp.mapper.LampServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MLampServiceService extends ServiceImpl<LampServiceMapper, LampService> {

    @Autowired
    private LampServiceMapper serviceMapper;

    @Override
    public List<LampService> list() {
        LambdaQueryWrapper<LampService> queryWrapper = new LambdaQueryWrapper<>(LampService.class);
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        return serviceMapper.selectList(queryWrapper);
    }

    @Override
    public boolean save(LampService entity) {
        super.save(entity);
        return true;
    }
//
//    @Override
//    public boolean saveOrUpdateBatch(Collection<LampService> entityList) {
//        List<LampService> saveList = entityList.stream().filter(service -> Objects.isNull(service.getId())).collect(Collectors.toList());
//        saveBatch(saveList);
//        List<LampService> updateList = entityList.stream().filter(service -> Objects.nonNull(service.getId())).collect(Collectors.toList());
//        updateBatchById(updateList);
//        return true;
//    }
//
//    @Override
//    public boolean saveBatch(Collection<LampService> entityList) {
//        super.saveBatch(entityList);
//        for (LampService service : entityList) {
//            LampServiceMonth serviceMonth = LampServiceMonth.generate(service);
//            serviceMonthService.save(serviceMonth);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean updateBatchById(Collection<LampService> entityList) {
//        super.updateBatchById(entityList);
//        for (LampService service : entityList) {
//            if (Objects.isNull(service.getServiceMonthList()) || service.getServiceMonthList().isEmpty()) {
//                continue;
//            }
//            serviceMonthService.updateBatchById(service.getServiceMonthList());
//        }
//        return true;
//    }
//
//    public void removeByMemberId(Long memberId) {
//        LambdaQueryWrapper<LampService> queryWrapper = new LambdaQueryWrapper<>(LampService.class);
//        queryWrapper.eq(LampService::getMemberId, memberId);
//        BaseEntity.setDeleteFlagCondition(queryWrapper);
//        List<LampService> serviceList = serviceMapper.selectList(queryWrapper);
//        LambdaUpdateWrapper<LampService> updateWrapper = new LambdaUpdateWrapper<>(LampService.class);
//        updateWrapper.set(LampService::getDeleteFlag, true);
//        updateWrapper.eq(LampService::getMemberId, memberId);
//        serviceMapper.update(null, updateWrapper);
//        for (LampService service : serviceList) {
//            serviceMonthService.removeByServiceId(service.getId());
//        }
//    }
//
    public void setServiceList(LampMember member) {
        LambdaQueryWrapper<LampService> queryWrapper = new LambdaQueryWrapper<>(LampService.class);
        queryWrapper.eq(LampService::getMemberId, member.getId());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampService> serviceList = serviceMapper.selectList(queryWrapper);
        member.setServiceList(serviceList);
    }

}
