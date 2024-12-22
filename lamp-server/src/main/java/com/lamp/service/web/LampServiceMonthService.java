//package com.lamp.service.web;
//
//import cn.hutool.core.date.DateUtil;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.lamp.common.entity.BaseEntity;
//import com.lamp.entity.LampService;
//import com.lamp.entity.LampServiceMonth;
//import com.lamp.mapper.LampServiceMonthMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class LampServiceMonthService extends ServiceImpl<LampServiceMonthMapper, LampServiceMonth> {
//
//    @Autowired
//    private LampClientTrafficService clientTrafficService;
//
//    public void setServiceMonth(LampService service, Date current) {
//        int year = DateUtil.year(current);
//        int month = DateUtil.month(current) + 1;
//        LambdaQueryWrapper<LampServiceMonth> queryWrapper = new LambdaQueryWrapper<>(LampServiceMonth.class);
//        BaseEntity.setDeleteFlagCondition(queryWrapper);
//        queryWrapper.eq(LampServiceMonth::getServiceId, service.getId());
//        queryWrapper.eq(LampServiceMonth::getServiceYear, year);
//        queryWrapper.eq(LampServiceMonth::getServiceMonth, month);
//        List<LampServiceMonth> serviceMonthList = list(queryWrapper);
//        if (serviceMonthList.isEmpty()) {
//            LampServiceMonth serviceMonth = LampServiceMonth.generate(service);
//            save(serviceMonth);
//        }
//        serviceMonthList = list(queryWrapper);
//        for (LampServiceMonth serviceMonth : serviceMonthList) {
//            clientTrafficService.setClientTrafficList(serviceMonth);
//        }
////        service.setServiceMonthList(serviceMonthList);
//    }
//
//}
