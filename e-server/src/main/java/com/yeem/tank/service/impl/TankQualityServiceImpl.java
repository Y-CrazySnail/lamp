package com.yeem.tank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.tank.entity.TankQuality;
import com.yeem.tank.mapper.TankQualityMapper;
import com.yeem.tank.service.ITankQualityService;
import org.springframework.stereotype.Service;

@Service
public class TankQualityServiceImpl extends ServiceImpl<TankQualityMapper, TankQuality> implements ITankQualityService {

}
