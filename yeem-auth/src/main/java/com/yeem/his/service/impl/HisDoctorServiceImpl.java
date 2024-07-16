package com.yeem.his.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yeem.his.entity.HisDoctor;
import com.yeem.his.mapper.HisDoctorMapper;
import com.yeem.his.service.IHisDoctorService;
import org.springframework.stereotype.Service;

@Service
public class HisDoctorServiceImpl extends ServiceImpl<HisDoctorMapper, HisDoctor> implements IHisDoctorService {

}
