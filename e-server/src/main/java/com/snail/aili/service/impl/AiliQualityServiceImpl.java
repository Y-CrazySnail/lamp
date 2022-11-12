package com.snail.aili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snail.aili.entity.AiliQuality;
import com.snail.aili.mapper.AiliQualityMapper;
import com.snail.aili.service.IAiliQualityService;
import org.springframework.stereotype.Service;

@Service
public class AiliQualityServiceImpl extends ServiceImpl<AiliQualityMapper, AiliQuality> implements IAiliQualityService {
}
