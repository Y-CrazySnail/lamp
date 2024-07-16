package com.yeem.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.his.entity.HisPatient;
import com.yeem.his.mapper.HisPatientMapper;
import com.yeem.his.service.IHisPatientService;
import org.springframework.stereotype.Service;

@Service
public class HisPatientServiceImpl extends ServiceImpl<HisPatientMapper, HisPatient> implements IHisPatientService {

}
