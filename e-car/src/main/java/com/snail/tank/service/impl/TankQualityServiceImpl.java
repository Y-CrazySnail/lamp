package com.snail.tank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.tank.entity.TankQuality;
import com.snail.tank.mapper.TankQualityMapper;
import com.snail.tank.service.ITankQualityService;
import org.springframework.stereotype.Service;

@Service
public class TankQualityServiceImpl extends ServiceImpl<TankQualityMapper, TankQuality> implements ITankQualityService {

}
