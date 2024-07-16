package com.yeem.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.his.entity.HisAppointment;
import com.yeem.his.mapper.HisAppointmentMapper;
import com.yeem.his.service.IHisAppointmentService;
import org.springframework.stereotype.Service;

@Service
public class HisAppointmentServiceImpl extends ServiceImpl<HisAppointmentMapper, HisAppointment> implements IHisAppointmentService {

}
