package com.yeem.car.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeem.car.entity.CFQuality;
import com.yeem.car.mapper.CFQualityMapper;
import com.yeem.car.service.ICFQualityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CFQualityServiceImpl extends ServiceImpl<CFQualityMapper, CFQuality> implements ICFQualityService {

    @Override
    public List<CFQuality> list(String tenantNo, String queryKey) {
        return Collections.emptyList();
    }
}
