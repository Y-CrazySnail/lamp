package com.lamp.service.manage;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lamp.common.entity.BaseEntity;
import com.lamp.entity.LampService;
import com.lamp.entity.LampServiceMonth;
import com.lamp.mapper.LampServiceMonthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class MLampServiceMonthService extends ServiceImpl<LampServiceMonthMapper, LampServiceMonth> {

    @Autowired
    private LampServiceMonthMapper serviceMonthMapper;

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

    public void setServiceMonthList(LampService service) {
        LambdaQueryWrapper<LampServiceMonth> queryWrapper = new LambdaQueryWrapper<>(LampServiceMonth.class);
        queryWrapper.eq(LampServiceMonth::getServiceId, service.getId());
        BaseEntity.setDeleteFlagCondition(queryWrapper);
        List<LampServiceMonth> serviceMonthList = serviceMonthMapper.selectList(queryWrapper);
        for (LampServiceMonth serviceMonth : serviceMonthList) {
            clientTrafficService.setClientTrafficList(serviceMonth);
        }
        service.setServiceMonthList(serviceMonthList);
    }
}
